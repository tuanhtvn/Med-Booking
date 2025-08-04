package app

import (
	"server/internal/handlers"
	"server/internal/repositories"
	"server/internal/routes"
	"server/internal/services"

	"gorm.io/gorm"
)

type UserModule struct {
	route routes.Route
}

func NewUserModule(db *gorm.DB) *UserModule {
	// repository
	repo := repositories.NewUserRepository(db)
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
