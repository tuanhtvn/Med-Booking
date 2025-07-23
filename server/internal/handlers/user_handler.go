package handlers

import (
	"log"
	"server/internal/services"

	"github.com/gin-gonic/gin"
)

type UserHandler struct {
	service services.UserService
}

func NewUserHandler(service services.UserService) *UserHandler {
	return &UserHandler{
		service: service,
	}
}

func (uh *UserHandler) GetAllUsers(ctx *gin.Context) {
	log.Println("Run get all user")

	uh.service.GetAllUsers()
}

func (uh *UserHandler) CreateUser(ctx *gin.Context) {
}

func (uh *UserHandler) GetUserByID(ctx *gin.Context) {
}

func (uh *UserHandler) UpdateUser(ctx *gin.Context) {
}

func (uh *UserHandler) DeleteUser(ctx *gin.Context) {
}
