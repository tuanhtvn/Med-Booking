package routes

import (
	"server/internal/handlers"

	"github.com/gin-gonic/gin"
)

type UserRoute struct {
	handler *handlers.UserHandler
}

func NewUserRoute(handler *handlers.UserHandler) *UserRoute {
	return &UserRoute{
		handler: handler,
	}
}

func (ur *UserRoute) Register(r *gin.RouterGroup) {
	users := r.Group("/users")
	{
		users.GET("/", ur.handler.GetAllUser)
	}
}
