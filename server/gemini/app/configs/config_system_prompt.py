def load_system_prompt():
    try:

        with open("./promtps/system_prompt.md", "r") as f:
            return f.read()
    except FileNotFoundError:
        return None
