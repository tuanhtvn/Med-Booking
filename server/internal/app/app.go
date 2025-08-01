package app

import (
	"server/internal/configs"
	"server/internal/middlewares"
	"server/internal/routes"
	"strconv"

	"github.com/gin-gonic/gin"
)

type Module interface {
	Routes() routes.Route
}

type Application struct {
	Config *configs.Config
	route  *gin.Engine
}

func NewApplication(config *configs.Config) *Application {

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
		Config: config,
		route:  r,
	}
}

func (app *Application) Run() error {
	// _, err := gorm.Open(
	// 	mysql.Open(app.Config.Server.ConnString),
	// 	&gorm.Config{},
	// )

	// if err != nil {
	// 	return err
	// }
	// log.Printf("Database connection established")

	return app.route.Run(":" + app.Config.Server.Port)
}

func getModuleRoutes(modules []Module) []routes.Route {
	routeList := make([]routes.Route, len(modules))
	for i, module := range modules {
		routeList[i] = module.Routes()
	}
	return routeList
}
