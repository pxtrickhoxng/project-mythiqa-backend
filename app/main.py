from fastapi import FastAPI
from db import get_user

app = FastAPI()

@app.get("/")
def root():
    return {"Hello": "World"}

@app.get("/user/{user_id}")
def root(user_id: str):
    return get_user(user_id)