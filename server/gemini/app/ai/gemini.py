import logging

from google import genai
from google.genai.types import GenerateContentConfig, ThinkingConfig

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


class Gemini:

    def __init__(
        self,
        google_api_key: str,
        model_id: str = "gemini-2.5-flash",
        system_prompt: str = None,
        thinking_budget: int = 0,
    ):
        self.client = genai.Client(api_key=google_api_key)
        self.model = self.client.aio.chats.create(
            model=model_id,
            config=GenerateContentConfig(
                system_instruction=system_prompt,
                thinking_config=ThinkingConfig(
                    thinking_budget=thinking_budget
                ),
            ),
        )

    async def chat(self, prompt: str) -> str:
        response = await self.model.send_message(prompt)
        text = response.candidates[0].content.parts[0].text
        logger.info(f"Gemini response: {text}")
        return text
