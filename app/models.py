from typing import Optional, List
from sqlmodel import SQLModel, Field, Column
from sqlalchemy import ARRAY, String
from datetime import date
from enum import Enum

class UserRole(str, Enum):
    admin = "admin"
    user = "user"

class Users(SQLModel, table=True):
    __tablename__ = "Users" #type: ignore
    user_id: str = Field(primary_key=True)
    username: str = Field(index=True)
    email: str
    description: Optional[str] = Field(default=None, nullable=True)
    profile_background_img_url: Optional[str] = Field(default=None, nullable=True)
    created_at: date = Field(default_factory=date.today)
    user_profile_url: Optional[str] = Field(default=None, nullable=True)
    role: UserRole = UserRole.user
    
class Books(SQLModel, table=True):
    __tablename__ = "Books" #type: ignore
    book_id: Optional[int] = Field(default=None, primary_key=True)
    user_id: str = Field(foreign_key="Users.user_id")
    book_name: str = Field(max_length=255)
    book_type: Optional[str] = Field(default=None, max_length=50)
    description: Optional[str] = Field(default=None)
    genre: Optional[str] = Field(default=None, max_length=100)
    target_audience: Optional[str] = Field(default=None, max_length=50)
    content_warnings: Optional[List[str]] = Field(
        default=None,
        sa_column=Column(ARRAY(String)) 
    )
    book_cover_url: Optional[str] = Field(default=None, max_length=255)
    created_at: date = Field(default_factory=date.today)
