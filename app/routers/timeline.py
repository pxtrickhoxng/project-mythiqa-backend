from fastapi import APIRouter, Depends, HTTPException
from fastapi_clerk_auth import HTTPAuthorizationCredentials
from schemas import TimelineCardContent #type: ignore
from dependencies import clerk_auth_guard, timeline
from boto3.dynamodb.conditions import Key
from sqlmodel import Session, select
from database import engine
from models import Books
from util.verify_user_owns_book import verify_user_owns_book

router = APIRouter(prefix="/api/timeline")

@router.post("/{book_id}/create-timeline-card", status_code=200)
def create_timeline_card(book_id: str, cardData: TimelineCardContent, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    verify_user_owns_book(book_id, credentials)
    
    timelinecard_payload = {
        "book_id": book_id,
        "index": cardData.index,
        "user_id": cardData.userId,
        "event_tag": cardData.eventTag.model_dump(),  # convert Pydantic object to dict
        "card_title": cardData.cardTitle,
        "card_color": cardData.cardColor,
        "chapter": cardData.chapter,
        "details": [detail.model_dump() for detail in cardData.details]  # convert list of Pydantic objects to list of dicts
    }

    try:
        timeline.put_item(Item=timelinecard_payload)
    except Exception:
        raise HTTPException(status_code=500, detail="Failed to create timeline card")
    return {'message': 'success'}

@router.put("/{book_id}/update-timeline-card/{index}", status_code=200)
def update_timeline_card(book_id: str, index: int, cardData: TimelineCardContent, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    verify_user_owns_book(book_id, credentials)
    
    timelinecard_payload = {
        "book_id": book_id,
        "index": index,
        "user_id": cardData.userId,
        "event_tag": cardData.eventTag.model_dump(),  # convert Pydantic object to dict
        "card_title": cardData.cardTitle,
        "card_color": cardData.cardColor,
        "chapter": cardData.chapter,
        "details": [detail.model_dump() for detail in cardData.details]  # convert list of Pydantic objects to list of dicts
    }

    try:
        timeline.put_item(Item=timelinecard_payload)
    except Exception:
        raise HTTPException(status_code=500, detail="Failed to update timeline card")
    return {'message': 'success'}

@router.get("/{book_id}/get-timeline-cards", status_code=200)
def get_timeline_cards(book_id: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    verify_user_owns_book(book_id, credentials)
    
    response = timeline.query(
        KeyConditionExpression=Key('book_id').eq(book_id),
        ScanIndexForward=True,
    )
    
    items = response.get('Items', [])
    
    return items


@router.get("/{book_id}/latest-timeline-index", status_code=200)
def get_latest_timeline_index(book_id: str, credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard) ):
    verify_user_owns_book(book_id, credentials)

    response = timeline.query(
        KeyConditionExpression=Key('book_id').eq(book_id),
        Select='COUNT' 
    )
    
    timeline_count = response.get('Count', 0)

    return {"latest_timeline_index": timeline_count + 1}

@router.delete("/{book_id}/delete-timeline-card/{index}", status_code=200)
def delete_timeline_card(book_id: str, index: int , credentials: HTTPAuthorizationCredentials = Depends(clerk_auth_guard)):
    verify_user_owns_book(book_id, credentials)
    
    try:
        timeline.delete_item(Key={'book_id': book_id, 'index': index})
    except Exception:
        raise HTTPException(status_code=500, detail="Failed to delete timeline card")
    return {'message': 'success'}