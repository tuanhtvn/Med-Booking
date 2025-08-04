package app

import (
	"context"
	"log"
	"net/http"
	"os"
	"os/signal"
	"server/internal/configs"
	"server/internal/init/db"
	"server/internal/middlewares"
	"server/internal/routes"
	"server/internal/validation"
	"strconv"
	"syscall"
	"time"

	"github.com/gin-gonic/gin"
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
	
	// Initialize Database postgres
	connStr := config.Server.ConnString

	if err := db.InitDBPostGres(connStr); err != nil {
		log.Fatal("unable to connect to db")
	}

	timeCheck, _ := strconv.Atoi(config.Server.RateTimeCheck)
	go middlewares.CleanupClient(timeCheck)

	requestRate, _ := strconv.Atoi(config.Server.RateRequest)
	brustRate, _ := strconv.Atoi(configs.NewConfig().Server.RateBrust)

	r.Use(middlewares.LoggerMiddleware(), middlewares.RateLimitingMiddleware(requestRate, brustRate))

	modules := []Module{
		NewUserModule(db.DBPostGres),
	}

	routes.RegisterRoute(r, getModuleRoutes(modules)...)

	return &Application{
		config:  config,
		route:   r,
		modules: modules,
	}
}

func (app *Application) Run() error {
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

	ctx, cancel := context.WithTimeout(context.Background(), 15*time.Second)
	defer cancel()

	if err := srv.Shutdown(ctx); err != nil {
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
