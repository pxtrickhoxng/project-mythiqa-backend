from fastapi import FastAPI, Depends, HTTPException, Header, File, UploadFile, Form
from fastapi_clerk_auth import ClerkConfig, ClerkHTTPBearer, HTTPAuthorizationCredentials
from fastapi.responses import JSONResponse
from fastapi.encoders import jsonable_encoder
from models import Users, Books
from sqlmodel import Session, select, func
from fastapi.middleware.cors import CORSMiddleware
from database import engine
from schemas import CreateUser, UpdateUser, ChapterContent #type: ignore
from dotenv import load_dotenv
import os
import boto3
from botocore.exceptions import ClientError
import json
import uuid
from datetime import datetime, UTC
from boto3.dynamodb.conditions import Key, Attr
from util.get_token import get_token_from_header

load_dotenv()

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000"],  
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

clerk_config = ClerkConfig(jwks_url="https://working-leopard-42.clerk.accounts.dev/.well-known/jwks.json")
clerk_auth_guard = ClerkHTTPBearer(config=clerk_config)

s3 = boto3.resource(
    's3',
    aws_access_key_id=os.getenv("AWS_ACCESS_KEY_ID"),
    aws_secret_access_key=os.getenv("AWS_SECRET_ACCESS_KEY"),
    region_name=os.getenv("AWS_DEFAULT_REGION")
)

dynamodb = boto3.resource(
    'dynamodb',
    aws_access_key_id=os.getenv("AWS_ACCESS_KEY_ID"),
    aws_secret_access_key=os.getenv("AWS_SECRET_ACCESS_KEY"),
    region_name=os.getenv("AWS_DEFAULT_REGION")
)

chapters = dynamodb.Table('Chapters') 

@app.get('/')
async def read_root(credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    return JSONResponse(content=jsonable_encoder(credentials))

@app.get("/api/users/all")
def get_all_users(credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    with Session(engine) as session:
        users = session.exec(select(Users)).all()
    return users

@app.get("/api/users/{username}")
def user_data(username: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):    
    with Session(engine) as session:
        user = session.exec(
            select(Users).where(func.lower(Users.username) == username.lower())
        ).first()
    return user

@app.get("/api/users/{username}/profile-img")
def user_profile_img(username: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    with Session(engine) as session:
        user_profile_img_url = session.exec(
            select(Users.user_profile_url).where(func.lower(Users.username) == username.lower())
        ).first()
    return {"user_profile_img_url": user_profile_img_url }

@app.post("/api/users/create", status_code=201)
def create_user(user: CreateUser, authorization: str = Header(None)): 
    if authorization != f"Bearer {os.getenv("CLERK_SECRET_KEY")}":
        raise HTTPException(status_code=401, detail='Unauthorized')

    with Session(engine) as session:
        existing_user = session.exec(select(Users).where(Users.user_id == user.user_id)).first()
        if existing_user:
            return {"message": "User already exists"}
        new_user = Users(
            user_id=user.user_id,
            username=user.username,
            email=user.email,
            description=user.description,
            profile_background_img_url=user.profile_background_img_url,
            user_profile_url=user.user_profile_url,
            role=user.role
        )
        session.add(new_user)
        session.commit()
        session.refresh(new_user)
    return {"message": "User created"}

@app.put("/api/users/update", status_code=200)
def update_user(user: UpdateUser, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    if credentials.decoded is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    user_id = credentials.decoded['sub']
    with Session(engine) as session:
        db_user = session.exec(select(Users).where(Users.user_id == user_id)).first()
        if not db_user:
            raise HTTPException(status_code=404, detail="User not found")
        db_user.username = user.username
        db_user.description = user.description
        db_user.profile_background_img_url = user.profile_background_img_url
        db_user.user_profile_url = user.user_profile_url
        session.add(db_user)
        session.commit()
        session.refresh(db_user)
    return {"message": "User updated"}
   
@app.delete("/api/users/delete", status_code=200)
def delete_user(user_id: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    with Session(engine) as session:
        db_user = session.exec(select(Users).where(Users.user_id == user_id)).first()
        if not db_user:
            raise HTTPException(status_code=404, detail="User not found")
        session.delete(db_user)
        session.commit()
    return {"message": "User deleted"}
   
@app.post("/api/upload", status_code=200)
async def upload_file(
    bg_img_file: UploadFile = File(required=True), 
    user_profile_img_file: UploadFile = File(required=True),
    bucket: str = Form(required=True), 
    credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard) 
):
    if credentials.decoded is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    try:
        user_id = credentials.decoded['sub']
        bg_img_object_key = f"{user_id}/profile/{bg_img_file.filename}"
        profile_img_object_key = f"{user_id}/profile/{user_profile_img_file.filename}"
        
        s3_client = boto3.client('s3')

        #upload user background img
        s3_client.upload_fileobj(bg_img_file.file, bucket, bg_img_object_key)
        
        #upload user profile img
        s3_client.upload_fileobj(user_profile_img_file.file, bucket, profile_img_object_key)
        
        region = os.getenv("AWS_DEFAULT_REGION")
        
        bg_img_url = f"https://{bucket}.s3.{region}.amazonaws.com/{bg_img_object_key}"
        profile_img_url = f"https://{bucket}.s3.{region}.amazonaws.com/{profile_img_object_key}"
        
        return {"bg_img_url": bg_img_url, "profile_img_url": profile_img_url}
    
    except ClientError as e:
        return JSONResponse(status_code=500, content={"success": False, "error": str(e)})
    except Exception as e:
        return JSONResponse(status_code=500, content={"success": False, "error": str(e)})

@app.post("/api/{user_id}/create-story", status_code=200)
async def create_story(
    user_id: str,
    book_name: str = Form(...),
    book_type: str = Form(...),
    description: str = Form(None),
    genre: str = Form(None),
    target_audience: str = Form(None),
    content_warnings: str = Form("[]"), 
    book_cover: UploadFile = File(None),
    bucket: str = Form(required=True),
    credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)
):
    if credentials.decoded is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    
    authenticated_user_id = credentials.decoded['sub']
    if user_id != authenticated_user_id:
        raise HTTPException(status_code=403, detail="Unauthorized to create story for this user")

    try:
        parsed_content_warnings = json.loads(content_warnings)
    except json.JSONDecodeError:
        raise HTTPException(status_code=400, detail="Invalid JSON in content_warnings")
    
    book_cover_url = None

    with Session(engine) as session:
        existing_book = session.exec(select(Books).where(Books.book_name == book_name)).first()
        if existing_book:
            raise HTTPException(status_code=409, detail="Book already exists")
        
        if book_cover:
            s3_client = boto3.client('s3')
            object_key = f"{user_id}/books/{book_cover.filename}"
            s3_client.upload_fileobj(book_cover.file, bucket, object_key)
            region = os.getenv("AWS_DEFAULT_REGION")
            book_cover_url = f"https://{bucket}.s3.{region}.amazonaws.com/{object_key}"
        
        new_book = Books(
            user_id=user_id,
            book_name=book_name,
            book_type=book_type,
            description=description,
            genre=genre,
            target_audience=target_audience,
            content_warnings=parsed_content_warnings, 
            book_cover_url=book_cover_url,
        )
        session.add(new_book)
        session.commit()
        session.refresh(new_book)
    return {"message": "User created", "new_book": new_book}

@app.post("/api/{user_id}/stories", status_code=200)
def fetch_stories(user_id: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    if credentials.decoded is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    
    authenticated_user_id = credentials.decoded['sub']
    if user_id != authenticated_user_id:
        raise HTTPException(status_code=403, detail="Unauthorized to create story for this user")

    with Session(engine) as session:
        books = session.exec(select(Books).where(Books.user_id == user_id)).all()
    
    return books

@app.get("/api/{book_id}", status_code=200)
def get_story(book_id: str, token: str = Depends(get_token_from_header)):
    if token is None or token != os.getenv("FRONTEND_API_KEY"):
        raise HTTPException(status_code=401, detail="Invalid token")
    
    with Session(engine) as session:
        book = session.exec(select(Books).where(Books.book_id == book_id)).first()
        
    return book

@app.post("/api/{book_id}/create-chapter/{chapter_number}", status_code=200)
def create_chapter(
    book_id: str,
    chapter_number: str, 
    chapter_name: str = Form(...),
    chapter_content: str = Form(...),
    credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)
):
    if credentials.decoded is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    
    try:
        chapter_content_json = json.loads(chapter_content)
    except json.JSONDecodeError:
        raise HTTPException(status_code=400, detail="Invalid JSON in chapter_content")
    
    chapter_id = str(uuid.uuid4())
    creation_date = datetime.now(UTC).isoformat()

    chapter_payload =  {
        "book_id": book_id,
        "chapter_number": chapter_number,
        "creation_date": creation_date,
        "chapter_name": chapter_name,
        "chapter_content": chapter_content_json,
        "chapter_id": chapter_id
    }
    
    try:
        chapters.put_item(Item=chapter_payload)
    except Exception:
        raise HTTPException(status_code=500, detail="Failed to write chapter to database")
    return {'message': 'success'}

@app.get("/api/{book_id}/get-new-chapter-number", status_code=200)
def chapter_number(book_id: str, token: str = Depends(get_token_from_header)):
    if token is None or token != os.getenv("FRONTEND_API_KEY"):
        raise HTTPException(status_code=401, detail="Invalid token")

    response = chapters.query(
        KeyConditionExpression=Key('book_id').eq(book_id),
        Select='COUNT' 
    )
    
    chapter_count = response.get('Count', 0)

    return {"chapter_number": chapter_count + 1}

@app.get("/api/get-chapters/{book_id}", status_code=200)
def get_chapters(book_id: str, token: str = Depends(get_token_from_header)):
    if token is None or token != os.getenv("FRONTEND_API_KEY"):
        raise HTTPException(status_code=401, detail="Invalid token")
    
    response = chapters.query(
        KeyConditionExpression=Key('book_id').eq(book_id),
        ProjectionExpression='chapter_id, chapter_number, chapter_name, creation_date'
    )
    item = response['Items']
    return(item)

@app.get("/api/book/{book_id}/chapter/{chapter}", status_code=200)
def get_chapter(book_id: str, chapter: int, token: str = Depends(get_token_from_header)):
    if token is None or token != os.getenv("FRONTEND_API_KEY"):
        raise HTTPException(status_code=401, detail="Invalid token")
    
    response = chapters.query(
        KeyConditionExpression=Key('book_id').eq(book_id),
        ScanIndexForward=True,
    )
    
    sideResponse = chapters.query(
        KeyConditionExpression=Key('book_id').eq(book_id),
        ProjectionExpression='chapter_number, chapter_name'
    )
    
    items = response.get('Items', [])
    sideItems = sideResponse.get('Items', [])
    
    current_index = chapter - 1

    prev_chapter = sideItems[current_index - 1] if current_index > 0 else None
    next_chapter = sideItems[current_index + 1] if current_index + 1 < len(sideItems) else None

    return {
        "currentChapter": items[current_index],
        "prevChapter": prev_chapter,
        "nextChapter": next_chapter
    }
    
@app.get("/api/{user_id}/num-of-books", status_code=200)
def get_num_of_books(user_id: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    if credentials.decoded is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    
    if user_id != credentials.decoded['sub']:
        raise HTTPException(status_code=401, detail="Unauthorized")
    
    with Session(engine) as session:
        num_of_books = session.exec(select(func.count()).select_from(Books).where(Books.user_id == user_id)).first()
    
    return {'count': num_of_books}