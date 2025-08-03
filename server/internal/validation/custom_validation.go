package validation

import (
	"fmt"
	"regexp"

	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
)

// Refe: https://github.com/quoctuan9901/khoa-hoc-lap-trinh-restful-api-voi-gin-framework/blob/main/lesson04-route-validation/utils
func RegisterCustomValidation(v *validator.Validate) {
	var slugRegex = regexp.MustCompile(`^[a-z0-9]+(?:[-.][a-z0-9]+)*$`)
	v.RegisterValidation("slug", func(fl validator.FieldLevel) bool {
		return slugRegex.MatchString(fl.Field().String())
	})

	var searchRegex = regexp.MustCompile(`^[a-zA-Z0-9\s]+$`)
	v.RegisterValidation("search", func(fl validator.FieldLevel) bool {
		return searchRegex.MatchString(fl.Field().String())
	})

}

func HandleValidationErrors(err error) gin.H {
	if validationError, ok := err.(validator.ValidationErrors); ok {
		errors := make(map[string]string)

		for _, e := range validationError {
			fmt.Printf("%+v", e.Value())
			fmt.Printf("%+v", e.Tag())
			fmt.Printf("%+v", e.Field())
		}

		return gin.H{"error": errors}

	}

	return gin.H{"error": "Yêu cầu không hợp lệ" + err.Error()}
}
