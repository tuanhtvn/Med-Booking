#ENVIRONMENT VARIABLES
include server/internal/init/environment/.env
export

# DATABASE CONNECTION STRING
CONN_STRING=postgresql://$(DB_USER):$(DB_PASSWORD)@$(DB_HOST):$(DB_PORT)/$(DB_NAME)?sslmode=disable

# GIN_MODE=release
GIN_MODE=debug

# MIGRATIONS PATH
MIGRATIONS_PATH=server/internal/init/db/migrations

# Run the application
start_db:
	docker compose up -d
stop_db:
	docker compose down
# Frontend
client:
	cd client/med-booking && \
	npm run dev

update_client:
	cd client/med-booking && \
	npm i

# Backend
server:
	cd server && \
	go run cmd/api/main.go

update_server:
	cd server && \
	go mod tidy

# Database backup and migration
# Backup and restore
export_db:
	docker exec -i postgres_db pg_dump -U root -d master-golang > ./backupdb-master-golang.sql

import_db:
	docker exec -i postgres_db psql -U root -d master-golang < ./backupdb-master-golang.sql

# Migrations
migrate_create:
	migrate create -ext sql -dir $(MIGRATIONS_PATH) -seq $(NAME)

migrate_up:
	migrate -path $(MIGRATIONS_PATH) -database "$(CONN_STRING)" up

migrate_down:
	migrate -path $(MIGRATIONS_PATH) -database "$(CONN_STRING)" down 1

migrate_down_n:
	migrate -path $(MIGRATIONS_PATH) -database "$(CONN_STRING)" down $(N)

migrate_force:
	migrate -path $(MIGRATIONS_PATH) -database "$(CONN_STRING)" force $(VERSION)

migrate_drop:
	migrate -path $(MIGRATIONS_PATH) -database "$(CONN_STRING)" drop

migrate_goto:
	migrate -path $(MIGRATIONS_PATH) -database "$(CONN_STRING)" goto $(VERSION)

.PHONY: server client update_client update_server export_db import_db migrate_create migrate_up migrate_down migrate_force migrate_drop migrate_goto migrate_down_n start_db
