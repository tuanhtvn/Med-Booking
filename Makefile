# Environment variables
include .env

export
# MongoDB database connection string
CONN_STRING=mongodb+srv://$(DB_USER):$(DB_PASS)@$(DB_HOST)/?retryWrites=true&w=majority&appName=$(DB_APP_NAME)

# Run server spring boot application
start-server:
	cd server/medical && export APP_MODE=$(APP_MODE) && \
	./mvnw spring-boot:run

# Run client react application
start-client:
	cd client/medical && \
	npm run dev

build-app:
	docker compose up      
