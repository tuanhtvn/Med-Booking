package app

import (
	"server/internal/handlers"
	"server/internal/repositories"
	"server/internal/routes"
	"server/internal/services"
)

type UserModule struct {
	route routes.Route
}

func NewUserModule() *UserModule {
	// repository
	repo := repositories.NewUserRepository()
	// service
	service := services.NewUserService(repo)
	// handler
	hanler := handlers.NewUserHandler(service)
	//router
	router := routes.NewUserRoute(hanler)
	return &UserModule{route: router}
}

func (m *UserModule) Routes() routes.Route {
	return m.route
}
