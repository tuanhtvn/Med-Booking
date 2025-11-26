from fastapi import APIRouter
from models.chat_request import ChatRequest
from models.chat_response import ChatResponse
from services import chat_service

router = APIRouter(prefix="/chats", tags=["chats"])


@router.post("", response_model=ChatResponse)
async def chat_endpoint(request: ChatRequest):
    text = await chat_service.chat(prompt=request)
    return ChatResponse(response=text)
