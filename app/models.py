from typing import Optional
from sqlmodel import SQLModel, Field
from datetime import date
from enum import Enum

class UserRole(str, Enum):
    admin = "admin"
    user = "user"

class Users(SQLModel, table=True):
    # Ignore pylance error
    __tablename__ = "Users"
    user_id: str = Field(primary_key=True)
    username: str = Field(index=True)
    email: str
    description: Optional[str] = Field(default=None, nullable=True)
    profile_background_img_url: Optional[str] = Field(default=None, nullable=True)
    created_at: date = Field(default_factory=date.today)
    user_profile_url: Optional[str] = Field(default=None, nullable=True)
    role: UserRole = UserRole.user
