package handlers

import (
	"net/http"
	"server/internal/models"
	"server/internal/services"
	"server/internal/utils"
	"strconv"

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
	users, err := uh.service.GetAllUsers()
	if err != nil {
		utils.ResponseError(ctx, err)
		return
	}
	utils.ResponseSuccess(ctx, http.StatusOK, users)
}

func (uh *UserHandler) CreateUser(ctx *gin.Context) {
	var user models.User
	if err := ctx.ShouldBindJSON(&user); err != nil {
		utils.ResponseError(ctx, utils.WrapError(err, "Bad request", utils.ErrCodeBadRequest))
		return
	}

	if err := uh.service.CreateUser(&user); err != nil {
		utils.ResponseError(ctx, err)
		return
	}
	utils.ResponseSuccess(ctx, http.StatusCreated, user)

}

func (uh *UserHandler) GetUserByID(ctx *gin.Context) {
	id, err := strconv.Atoi(ctx.Param("id"))
	if err != nil {
		utils.ResponseError(ctx, utils.WrapError(err, "Invaild user ID", utils.ErrCodeBadRequest))
		return
	}
	user, err := uh.service.GetUserByID(id)
	if err != nil {
		utils.ResponseError(ctx, err)
		return
	}
	utils.ResponseSuccess(ctx, http.StatusOK, user)
}

func (uh *UserHandler) UpdateUser(ctx *gin.Context) {
}

func (uh *UserHandler) DeleteUser(ctx *gin.Context) {
}
