package app

import (
	"context"
	"fmt"
	"log"
	"net/http"
	"os"
	"os/signal"
	"server/internal/configs"
	"server/internal/middlewares"
	"server/internal/routes"
	"server/internal/validation"
	"strconv"
	"syscall"
	"time"

	"github.com/gin-gonic/gin"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

type Module interface {
	Routes() routes.Route
}

type Application struct {
	config  *configs.Config
	route   *gin.Engine
	modules []Module
}

func NewApplication(config *configs.Config) *Application {
	// Initialize validation
	validation.InitValidation()

	// Initialize the Gin router
	r := gin.Default()
	timeCheck, _ := strconv.Atoi(config.Server.RateTimeCheck)
	go middlewares.CleanupClient(timeCheck)

	requestRate, _ := strconv.Atoi(config.Server.RateRequest)
	brustRate, _ := strconv.Atoi(configs.NewConfig().Server.RateBrust)

	r.Use(middlewares.LoggerMiddleware(), middlewares.RateLimitingMiddleware(requestRate, brustRate))

	modules := []Module{
		NewUserModule(),
	}

	routes.RegisterRoute(r, getModuleRoutes(modules)...)

	return &Application{
		config:  config,
		route:   r,
		modules: modules,
	}
}

func (app *Application) Run() error {
	connStr := app.config.Server.ConnString

	config := &gorm.Config{
		Logger: logger.Default.LogMode(logger.Info),
	}

	DB, _ := gorm.Open(
		postgres.New(postgres.Config{
			DSN: connStr,
		}), config)

	sqlDB, err := DB.DB()

	if err != nil {
		return fmt.Errorf("error getting sql.DB: %w", err)
	}

	sqlDB.SetMaxOpenConns(50)
	sqlDB.SetMaxIdleConns(10)
	sqlDB.SetConnMaxLifetime(30 * time.Minute)
	sqlDB.SetConnMaxIdleTime(5 * time.Minute)

	ctx1, cancel1 := context.WithTimeout(context.Background(), 5*time.Second)

	defer cancel1()

	if err := sqlDB.PingContext(ctx1); err != nil {
		sqlDB.Close()
		return fmt.Errorf("⛔️ error DB ping: %w", err)
	}
	log.Printf("✅ Database connection established")

	srv := &http.Server{
		Addr:    app.config.Server.Port,
		Handler: app.route,
	}
	quit := make(chan os.Signal, 1)
	// syscall.SIGINT => Ctrl + C
	// syscall.SIGTERM => Kill Service
	// syscall.SIGHUP => Reload Service
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM, syscall.SIGHUP)

	go func() {
		log.Printf("✅ Server is running at %s \n", app.config.Server.Port)
		if err := srv.ListenAndServe(); err != http.ErrServerClosed {
			log.Fatalf("Failed to start server: %v", err)
		}
	}()

	<-quit
	log.Println("⛔️ Shutdown signal received ...")

	ctx2, cancel2 := context.WithTimeout(context.Background(), 15*time.Second)
	defer cancel2()

	if err := srv.Shutdown(ctx2); err != nil {
		log.Fatalf("🚀 Server forced to shutdown: %v", err)
	}

	log.Println("✅ Server exited gracefully")

	return nil
}

func getModuleRoutes(modules []Module) []routes.Route {
	routeList := make([]routes.Route, len(modules))
	for i, module := range modules {
		routeList[i] = module.Routes()
	}
	return routeList
}
