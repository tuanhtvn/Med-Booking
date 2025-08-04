package validation

import (
	"fmt"
	"regexp"
	"server/internal/utils"
	"strings"

	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
)

// Refe: https://github.com/quoctuan9901/khoa-hoc-lap-trinh-restful-api-voi-gin-framework/blob/main/lesson04-route-validation/utils
func RegisterCustomValidation(v *validator.Validate) {

	var blockedDomains = map[string]bool{
		"edu.vn":        true,
		"blacklist.com": true,
		"abc.com":       true,
	}

	v.RegisterValidation("email_domain", func(fl validator.FieldLevel) bool {
		email := fl.Field().String()
		if email == "" {
			return true // Skip validation if email is empty
		}
		parts := strings.Split(email, "@")
		if len(parts) != 2 {
			return false // Invalid email format
		}
		domain := utils.NormalizeString(parts[1])
		return !blockedDomains[domain]
	})

	v.RegisterValidation("password_strength", func(fl validator.FieldLevel) bool {
		password := fl.Field().String()
		if len(password) < 8 {
			return false // Password must be at least 8 characters long
		}
		hasUpper := regexp.MustCompile(`[A-Z]`).MatchString(password)
		hasLower := regexp.MustCompile(`[a-z]`).MatchString(password)
		hasNumber := regexp.MustCompile(`[0-9]`).MatchString(password)
		hasSpecial := regexp.MustCompile(`[!@#\$%\^&\*\(\)_\+\-=\[\]\{\};:'",.<>\/?\\|]`).MatchString(password)

		return hasUpper && hasLower && hasNumber && hasSpecial
	})
}

func HandleValidationErrors(err error) gin.H {
	if validationError, ok := err.(validator.ValidationErrors); ok {
		errors := make(map[string]string)

		for _, e := range validationError {
			switch e.Tag() {
			case "required":
				errors[e.Field()] = fmt.Sprintf("Trường %s là bắt buộc", e.Field())
			case "email":
				errors[e.Field()] = fmt.Sprintf("%s phải là địa chỉ hợp lệ", e.Field())
			case "email_domain":
				errors[e.Field()] = fmt.Sprintf("%s nằm trong danh sách bị cấm", e.Field())
			case "password_strength":
				errors[e.Field()] = fmt.Sprintf("%s phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt", e.Field())
			}
		}

		return gin.H{"error": errors}

	}

	return gin.H{"error": "Yêu cầu không hợp lệ" + err.Error()}
}
