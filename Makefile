# Environment variables
include .env
export

# Run server
# Java Spring Boot application
start-server-java:
	cd server/medical && \
	./mvnw spring-boot:run

# FastAPI Python application
start-server-python:
	cd server/gemini/app &&\
	uvicorn main:app --reload

# Run client react application
start-client:
	cd client/medical && \
	npm run dev

build-app:
	docker compose up -d

.PHONY: start-server start-client build-app