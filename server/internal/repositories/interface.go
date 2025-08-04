package repositories

import "server/internal/models"

type UserRepository interface {
	Create(user *models.User) error
	FindAll() ([]models.User, error)
	FindByID(id int) (*models.User, error)
	Update(user *models.User) error
	Delete(user *models.User) error
	FindByEmail(email string) (*models.User, error)
}
