# Environment variables
include .env
export

# Run server spring boot application
start-server:
	cd server/medical && \
	./mvnw spring-boot:run

# Run client react application
start-client:
	cd client/medical && \
	npm run dev

build-app:
	docker compose up -d

.PHONY: start-server start-client build-app