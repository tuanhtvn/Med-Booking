package handlers

import (
	"net/http"
	"server/internal/dto"
	"server/internal/services"
	"server/internal/utils"
	"server/internal/validation"

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

type GetUserByIDParams struct {
	ID int `uri:"id" binding:"required"`
}

func (uh *UserHandler) GetAllUsers(ctx *gin.Context) {
	users, err := uh.service.GetAllUsers()
	if err != nil {
		utils.ResponseError(ctx, err)
		return
	}
	userDTOs := dto.MapUsersToDTO(users)
	utils.ResponseSuccess(ctx, http.StatusOK, userDTOs)
}

func (uh *UserHandler) CreateUser(ctx *gin.Context) {
	var input dto.CreateUserInput
	if err := ctx.ShouldBindJSON(&input); err != nil {
		utils.ResponseValidator(ctx, validation.HandleValidationErrors(err))
		return
	}
	user := input.MapCreateInputToModel()
	if err := uh.service.CreateUser(user); err != nil {
		utils.ResponseError(ctx, err)
		return
	}
	userDTO := dto.MapUserToDTO(*user)
	utils.ResponseSuccess(ctx, http.StatusCreated, &userDTO)

}

func (uh *UserHandler) GetUserByID(ctx *gin.Context) {
	var params GetUserByIDParams
	if err := ctx.ShouldBindUri(&params); err != nil {
		utils.ResponseValidator(ctx, validation.HandleValidationErrors(err))
		return
	}
	user, err := uh.service.GetUserByID(params.ID)
	if err != nil {
		utils.ResponseError(ctx, err)
		return
	}
	userDTO := dto.MapUserToDTO(*user)
	utils.ResponseSuccess(ctx, http.StatusOK, userDTO)
}

func (uh *UserHandler) UpdateUser(ctx *gin.Context) {
	var params GetUserByIDParams
	if err := ctx.ShouldBindUri(&params); err != nil {
		utils.ResponseValidator(ctx, validation.HandleValidationErrors(err))
		return
	}

	var input dto.UpdateUserInput
	if err := ctx.ShouldBindJSON(&input); err != nil {
		utils.ResponseError(ctx, err)
		return
	}
	user := input.MapUpdateInputToModel()
	if err := uh.service.UpdateUser(params.ID, user); err != nil {
		utils.ResponseError(ctx, err)
		return
	}
	userDTO := dto.MapUserToDTO(*user)
	utils.ResponseSuccess(ctx, http.StatusOK, userDTO)
}

func (uh *UserHandler) DeleteUser(ctx *gin.Context) {
	var params GetUserByIDParams
	if err := ctx.ShouldBindUri(&params); err != nil {
		utils.ResponseValidator(ctx, validation.HandleValidationErrors(err))
		return
	}
	if err := uh.service.DeleteUser(params.ID); err != nil {
		utils.ResponseError(ctx, err)
		return
	}
	utils.ResponseSuccess(ctx, http.StatusNoContent, nil)
}
