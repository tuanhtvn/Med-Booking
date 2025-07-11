include server/internal/init/environment/.env
export

CONN_STRING=$(DB_USER):$(DB_PASSWORD)@tcp($(DB_HOST):$(DB_PORT))/$(DB_NAME)?charset=utf8mb4&parseTime=True&loc=Local

# GIN_MODE=release
GIN_MODE=debug

client:
	cd client/med-booking && \
	npm run dev

server:
	cd server && \
	go run cmd/api/main.go

update_client:
	cd client/med-booking && \
	npm i

.PHONY: server client