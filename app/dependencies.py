from fastapi_clerk_auth import ClerkConfig, ClerkHTTPBearer
import boto3
import os

clerk_config = ClerkConfig(jwks_url="https://working-leopard-42.clerk.accounts.dev/.well-known/jwks.json")
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