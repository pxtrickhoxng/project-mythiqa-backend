from fastapi_clerk_auth import ClerkConfig, ClerkHTTPBearer
import boto3
import os
from dotenv import load_dotenv

load_dotenv()

jwks_url = os.getenv("CLERK_JWKS_URL")
if jwks_url is None:
    raise ValueError("CLERK_JWKS_URL environment variable is not set")

clerk_config = ClerkConfig(jwks_url= jwks_url)
clerk_auth_guard = ClerkHTTPBearer(config=clerk_config)

s3 = boto3.resource(
    's3',
    aws_access_key_id=os.getenv("AWS_ACCESS_KEY_ID"),
    aws_secret_access_key=os.getenv("AWS_SECRET_ACCESS_KEY"),
    region_name=os.getenv("AWS_DEFAULT_REGION")
)

dynamodb = boto3.resource(
    'dynamodb',
    aws_access_key_id=os.getenv("AWS_ACCESS_KEY_ID"),
    aws_secret_access_key=os.getenv("AWS_SECRET_ACCESS_KEY"),
    region_name=os.getenv("AWS_DEFAULT_REGION")
)

chapters = dynamodb.Table('Chapters') # type: ignore
timeline = dynamodb.Table('UserTimeline') # type: ignore