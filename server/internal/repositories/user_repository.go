package repositories

import (
	"fmt"
	"log"
	"server/internal/models"

	"gorm.io/gorm"
)

type userRepository struct {
	db *gorm.DB
}

func NewUserRepository(db *gorm.DB) UserRepository {
	return &userRepository{
		db: db,
	}
}

func (ur *userRepository) FindAll() ([]models.User, error) {
	var users []models.User
	if err := ur.db.Find(&users).Error; err != nil {
		return nil, err
	}
	return users, nil
}

func (ur *userRepository) Create(user *models.User) error {
	if err := ur.db.Create(user).Error; err != nil {
		return fmt.Errorf("failed to create user: %w", err)
	}

	return nil
}

func (ur *userRepository) FindByID(id int) (*models.User, error) {
	log.Println("Find by ID")
	var data models.User
	if err := ur.db.First(&data, id).Error; err != nil {
		return nil, err
	}
	return &data, nil
}

func (ur *userRepository) Update() {

}

func (ur *userRepository) Delete() {

}
