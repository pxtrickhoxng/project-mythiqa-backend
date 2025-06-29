from pydantic import BaseModel, EmailStr
from typing import Optional, Dict, Any, List
from enum import Enum

class UserRole(str, Enum):
    admin = "admin"
    user = "user"

class CreateUser(BaseModel):
    user_id: str
    username: Optional[str] = None
    email: EmailStr
    description: Optional[str] = None
    profile_background_img_url: Optional[str] = None
    user_profile_url: Optional[str] = None
    role: UserRole = UserRole.user

class UpdateUser(BaseModel):
    username: Optional[str] = None
    user_profile_url: Optional[str] = None
    profile_background_img_url: Optional[str] = None
    description: Optional[str] = None

class ChapterContent(BaseModel):
    type: str = "doc"
    content: List[Dict[str, Any]] 
    