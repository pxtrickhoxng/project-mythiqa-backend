import os
from dotenv import load_dotenv
from sqlmodel import create_engine
from sqlalchemy import URL

load_dotenv()

url_object = URL.create(
    drivername="postgresql",
    username= os.getenv("AWS_RDS_USERNAME"),
    password= os.getenv("AWS_RDS_PASSWORD"),
    host= os.getenv("DATABASE_URL"),
    port= 5432
)

engine = create_engine(url_object)