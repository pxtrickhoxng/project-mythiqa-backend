from fastapi import APIRouter, Depends, HTTPException, UploadFile, File, Form
from fastapi_clerk_auth import HTTPAuthorizationCredentials
from dependencies import clerk_auth_guard, chapters
from database import engine
from sqlmodel import Session, select
import json
import os
import boto3
from models import Books
from util.get_token import get_token_from_header
import uuid
from datetime import datetime, UTC
from boto3.dynamodb.conditions import Key

router = APIRouter(prefix="/api/books")

@router.post("/{user_id}/create-story", status_code=200)
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

@router.get("/{user_id}/stories", status_code=200)
def fetch_stories(user_id: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    if credentials.decoded is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    
    authenticated_user_id = credentials.decoded['sub']
    if user_id != authenticated_user_id:
        raise HTTPException(status_code=403, detail="Unauthorized to create story for this user")

    with Session(engine) as session:
        books = session.exec(select(Books).where(Books.user_id == user_id)).all()
    
    return books

@router.get("/{book_id}", status_code=200)
def get_story(book_id: str, token: str = Depends(get_token_from_header)):
    if token is None or token != os.getenv("FRONTEND_API_KEY"):
        raise HTTPException(status_code=401, detail="Invalid token")
    
    with Session(engine) as session:
        book = session.exec(select(Books).where(Books.book_id == book_id)).first()
        
    return book

@router.post("/{book_id}/create-chapter/{chapter_number}", status_code=200)
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

@router.get("/{book_id}/get-new-chapter-number", status_code=200)
def chapter_number(book_id: str, token: str = Depends(get_token_from_header)):
    if token is None or token != os.getenv("FRONTEND_API_KEY"):
        raise HTTPException(status_code=401, detail="Invalid token")

    response = chapters.query(
        KeyConditionExpression=Key('book_id').eq(book_id),
        Select='COUNT' 
    )
    
    chapter_count = response.get('Count', 0)

    return {"chapter_number": chapter_count + 1}

@router.get("/get-chapters/{book_id}", status_code=200)
def get_chapters(book_id: str, token: str = Depends(get_token_from_header)):
    if token is None or token != os.getenv("FRONTEND_API_KEY"):
        raise HTTPException(status_code=401, detail="Invalid token")
    
    response = chapters.query(
        KeyConditionExpression=Key('book_id').eq(book_id),
        ProjectionExpression='chapter_id, chapter_number, chapter_name, creation_date'
    )
    item = response['Items']
    return(item)

@router.get("/{book_id}/chapter/{chapter}", status_code=200)
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
