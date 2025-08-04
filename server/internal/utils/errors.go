package utils

import "net/http"

type ErrorCode string

const (
	ErrCodeBadRequest ErrorCode = "BAD_REQUEST"
	ErrCodeNotFound   ErrorCode = "NOT_FOUND"
	ErrCodeConFlict   ErrorCode = "CONFLICT"
	ErrCodeInternal   ErrorCode = "INTERNAL_SERVER_ERROR"
)

type AppError struct {
	Message string
	Code    ErrorCode
	Err     error
}

func (ae *AppError) Error() string {
	return ""
}

func NewError(message string, code ErrorCode) error {
	return &AppError{
		Message: message,
		Code:    code,
	}
}

func WrapError(err error, message string, code ErrorCode) error {
	return &AppError{
		Message: message,
		Code:    code,
		Err:     err,
	}
}

func HttpStatusFromCode(code ErrorCode) int {
	switch code {
	case ErrCodeBadRequest:
		return http.StatusBadRequest
	case ErrCodeNotFound:
		return http.StatusNotFound
	case ErrCodeConFlict:
		return http.StatusConflict
	default:
		return http.StatusInternalServerError
	}
}
