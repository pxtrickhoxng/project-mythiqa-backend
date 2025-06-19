import os
from dotenv import load_dotenv
from sqlmodel import create_engine

load_dotenv()

conn_url = os.getenv("DATABASE_URL")
if conn_url is None:
    raise RuntimeError("DATABASE_URL environment variable is not set")
engine = create_engine(conn_url, echo=True, pool_pre_ping=True)