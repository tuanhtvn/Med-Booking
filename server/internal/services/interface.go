package services

import "server/internal/models"

type UserService interface {
	GetAllUsers() ([]models.User, error)
	CreateUser(user *models.User) error
	GetUserByID(id int) (*models.User, error)
	UpdateUser()
	DeleteUser()
}
