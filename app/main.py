from fastapi import FastAPI, Depends
from models import Users
from sqlmodel import Session, SQLModel, create_engine, select
import os
from dotenv import load_dotenv
from typing import Annotated
from fastapi.middleware.cors import CORSMiddleware

load_dotenv()

async def lifespan(app: FastAPI):
    create_db_and_tables()  
    yield

app = FastAPI(lifespan=lifespan)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

conn_url = os.getenv("DATABASE_URL")
engine = create_engine(conn_url)

def create_db_and_tables():
    SQLModel.metadata.create_all(engine)
    
def get_session():
    with Session(engine) as session:
        yield session
        
SessionDep = Annotated[Session, Depends(get_session)]


@app.get("/")
def get_all_users():
    with Session(engine) as session:
        users = session.exec(select(Users)).all()
    return users

@app.get("/api/users/{user_id}")
def user_data(user_id: str):
    with Session(engine) as session:
        user = session.exec(select(Users).where(Users.user_id == user_id)).first()
    return user