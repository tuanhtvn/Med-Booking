package utils

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func ResponseError(ctx *gin.Context, err error) {
	// If the error is an AppError, return a structured error response
	if appErr, ok := err.(*AppError); ok {
		status := HttpStatusFromCode(appErr.Code)
		response := gin.H{
			"error": appErr.Message,
			"code":  appErr.Code,
		}

		if appErr.Err != nil {
			response["detail"] = appErr.Err.Error()
		}

		ctx.JSON(status, response)
		return
	}
	// For other errors, return a generic internal server error
	ctx.JSON(http.StatusInternalServerError, gin.H{
		"error": err.Error(),
		"code":  ErrCodeInternal,
	})
}

func ResponseSuccess(ctx *gin.Context, statusCode int, data any) {
	ctx.JSON(statusCode, gin.H{
		"status": "success",
		"data":   data,
	})
}

func ResponseValidator(ctx *gin.Context, data any) {
	ctx.JSON(http.StatusBadRequest, data)
}
