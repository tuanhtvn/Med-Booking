from ai.gemini import Gemini
from configs.config_env import API_KEY
from configs.config_system_prompt import load_system_prompt
from models.chat_request import ChatRequest

system_prompt = load_system_prompt()

ai_platform = Gemini(google_api_key=API_KEY, system_prompt=system_prompt)


async def chat(prompt: ChatRequest) -> str:
    return await ai_platform.chat(prompt=prompt.prompt)
