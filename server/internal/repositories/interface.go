package repositories

import "server/internal/models"

type UserRepository interface {
	Create(user *models.User) error
	FindAll() ([]models.User, error)
	FindByID(id int) (*models.User, error)
	Update()
	Delete()
}
