package db

import (
	"context"
	"fmt"
	"log"
	"time"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

var DBPostGres *gorm.DB

func InitDBPostGres(connStr string) error {
	config := &gorm.Config{
		Logger: logger.Default.LogMode(logger.Info),
	}
	DBPostGres, _ = gorm.Open(
		postgres.New(postgres.Config{
			DSN: connStr,
		}), config)

	sqlDB, err := DBPostGres.DB()

	if err != nil {
		return fmt.Errorf("error getting sql.DB: %w", err)
	}

	sqlDB.SetMaxOpenConns(50)
	sqlDB.SetMaxIdleConns(10)
	sqlDB.SetConnMaxLifetime(30 * time.Minute)
	sqlDB.SetConnMaxIdleTime(5 * time.Minute)

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)

	defer cancel()

	if err := sqlDB.PingContext(ctx); err != nil {
		sqlDB.Close()
		return fmt.Errorf("⛔️ error DB ping: %w", err)
	}
	log.Printf("✅ Database connection established")
	return nil
}
