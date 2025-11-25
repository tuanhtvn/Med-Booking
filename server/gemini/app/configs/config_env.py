import os

API_KEY = os.getenv("GOOGLE_API_KEY")

if not API_KEY:
    raise ValueError("GOOGLE_API_KEY enviroment variable not set.")
