from fastapi import FastAPI, Depends
from fastapi_clerk_auth import ClerkConfig, ClerkHTTPBearer, HTTPAuthorizationCredentials
from fastapi.responses import JSONResponse
from fastapi.encoders import jsonable_encoder
from models import Users
from sqlmodel import Session, select
from fastapi.middleware.cors import CORSMiddleware
from database import engine

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

@app.get('/')
async def read_root(credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    return JSONResponse(content=jsonable_encoder(credentials))

@app.get("/api/all")
def get_all_users(credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    with Session(engine) as session:
        users = session.exec(select(Users)).all()
    return users

@app.get("/api/users/{user_id}")
def user_data(user_id: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    with Session(engine) as session:
        user = session.exec(select(Users).where(Users.user_id == user_id)).first()
    return user