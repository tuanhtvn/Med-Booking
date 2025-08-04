package dto

import (
	"server/internal/models"
	"server/internal/utils"
)

type UserDTO struct {
	ID        int         `json:"id"`
	Fullname  string      `json:"fullname"`
	Email     string      `json:"email"`
	Role      string      `json:"role"`
	Gender    string      `json:"gender"`
	Birthday  *utils.Date `json:"birthday"`
	CreatedAt *utils.Date `json:"created_at"`
	UpdatedAt *utils.Date `json:"updated_at"`
}

type CreateUserInput struct {
	Fullname string      `json:"fullname" binding:"required"`
	Email    string      `json:"email" binding:"required,email,email_domain"`
	Password string      `json:"password" binding:"required,password_strength"`
	Role     string      `json:"role" binding:"required"`
	Gender   string      `json:"gender" binding:"required"`
	Birthday *utils.Date `json:"birthday" binding:"required"`
}

type UpdateUserInput struct {
	Fullname string      `json:"fullname" binding:"omitempty"`
	Email    string      `json:"email" binding:"omitempty,email,email_domain"`
	Password string      `json:"password" binding:"omitempty,password_strength"`
	Role     string      `json:"role" binding:"omitempty"`
	Gender   string      `json:"gender" binding:"omitempty"`
	Birthday *utils.Date `json:"birthday" binding:"omitempty"`
}

func (input *CreateUserInput) MapCreateInputToModel() *models.User {
	return &models.User{
		Fullname: input.Fullname,
		Email:    input.Email,
		Password: input.Password,
		Role:     input.Role,
		Gender:   input.Gender,
		Birthday: input.Birthday,
	}
}

func (input *UpdateUserInput) MapUpdateInputToModel() *models.User {
	return &models.User{
		Fullname: input.Fullname,
		Email:    input.Email,
		Password: input.Password,
		Role:     input.Role,
		Gender:   input.Gender,
		Birthday: input.Birthday,
	}
}

func MapUserToDTO(user models.User) *UserDTO {
	return &UserDTO{
		ID:        user.Id,
		Fullname:  user.Fullname,
		Email:     user.Email,
		Role:      user.Role,
		Gender:    user.Gender,
		Birthday:  user.Birthday,
		CreatedAt: user.CreatedAt,
		UpdatedAt: user.UpdatedAt,
	}

}

func MapUsersToDTO(users []models.User) []UserDTO {
	userDTOs := make([]UserDTO, 0, len(users))

	for _, user := range users {
		userDTO := UserDTO{
			ID:        user.Id,
			Fullname:  user.Fullname,
			Email:     user.Email,
			Role:      user.Role,
			Gender:    user.Gender,
			Birthday:  user.Birthday,
			CreatedAt: user.CreatedAt,
			UpdatedAt: user.UpdatedAt,
		}
		userDTOs = append(userDTOs, userDTO)
	}

	return userDTOs
}
