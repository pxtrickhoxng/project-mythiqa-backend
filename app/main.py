from fastapi import FastAPI, Depends, HTTPException, Header, File, UploadFile, Form
from fastapi_clerk_auth import ClerkConfig, ClerkHTTPBearer, HTTPAuthorizationCredentials
from fastapi.responses import JSONResponse
from fastapi.encoders import jsonable_encoder
from models import Users
from sqlmodel import Session, select, func
from fastapi.middleware.cors import CORSMiddleware
from database import engine
from schemas import CreateUser, DeleteUser, UpdateUser # type: ignore
from dotenv import load_dotenv
import os
import boto3
from botocore.exceptions import ClientError

load_dotenv()

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  
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
 
@app.get("/api/test", status_code=200) 
def test(credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    if credentials.decoded is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    user_id = credentials.decoded['sub']
    print(user_id)

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
def delete_user(user_id: DeleteUser, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    with Session(engine) as session:
        db_user = session.exec(select(Users).where(Users.user_id == user_id.user_id)).first()
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