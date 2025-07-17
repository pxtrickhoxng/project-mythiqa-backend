from fastapi import APIRouter, Depends, Header, HTTPException, UploadFile, File, Form
from fastapi.responses import JSONResponse
from sqlmodel import Session, select
from sqlalchemy import func
from models import Users
from schemas import CreateUser, UpdateUser #type: ignore
from dependencies import clerk_auth_guard, s3, dynamodb
from database import engine
from fastapi_clerk_auth import HTTPAuthorizationCredentials
from botocore.exceptions import ClientError
import os
import boto3
from models import Books

router = APIRouter(prefix="/api/users")

@router.get("/{username}")
def user_data(username: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):    
    with Session(engine) as session:
        user = session.exec(
            select(Users).where(func.lower(Users.username) == username.lower())
        ).first()
    return user

@router.get("/{username}/profile-img")
def user_profile_img(username: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    with Session(engine) as session:
        user_profile_img_url = session.exec(
            select(Users.user_profile_url).where(func.lower(Users.username) == username.lower())
        ).first()
    return {"user_profile_img_url": user_profile_img_url }

@router.post("/create", status_code=201)
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

@router.put("/update", status_code=200)
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
   
@router.delete("/delete", status_code=200)
def delete_user(user_id: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    with Session(engine) as session:
        db_user = session.exec(select(Users).where(Users.user_id == user_id)).first()
        if not db_user:
            raise HTTPException(status_code=404, detail="User not found")
        session.delete(db_user)
        session.commit()
    return {"message": "User deleted"}

##### IMPLEMENT DELETING PREVIOUS IMAGES AS WELL
@router.post("/upload-imgs", status_code=200)
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

@router.get("/{user_id}/num-of-books", status_code=200)
def get_num_of_books(user_id: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    if credentials.decoded is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    
    if user_id != credentials.decoded['sub']:
        raise HTTPException(status_code=401, detail="Unauthorized")
    
    with Session(engine) as session:
        num_of_books = session.exec(select(func.count()).select_from(Books).where(Books.user_id == user_id)).first()
    
    return {'count': num_of_books}