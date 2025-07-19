from fastapi import HTTPException
from fastapi_clerk_auth import HTTPAuthorizationCredentials
from sqlmodel import Session, select
from models import Books
from database import engine

def verify_user_owns_book(
    book_id: str, 
    credentials: HTTPAuthorizationCredentials
) -> None:
    if credentials.decoded is None:
        raise HTTPException(status_code=401, detail="Invalid token")
    
    user_id = credentials.decoded['sub']
    
    with Session(engine) as session:
        book = session.exec(
            select(Books).where(Books.book_id == book_id, Books.user_id == user_id)
        ).first()
    
    if not book:
        raise HTTPException(status_code=403, detail="You do not own this book and cannot perform this action")
