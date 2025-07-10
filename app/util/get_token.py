from fastapi import Request, HTTPException

def get_token_from_header(request: Request):
    auth = request.headers.get("Authorization")
    if not auth or not auth.startswith("Bearer "):
        raise HTTPException(status_code=401, detail="Invalid or missing token")
    token = auth.split(" ")[1]
    return token