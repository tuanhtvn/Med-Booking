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

func (ur *userRepository) Update(user *models.User) error {
	log.Println("Run repository update user")
	if err := ur.db.Save(user).Error; err != nil {
		return fmt.Errorf("failed to update user: %w", err)
	}
	return nil

}

func (ur *userRepository) Delete(user *models.User) error {
	log.Println("Run repository delete user")
	if err := ur.db.Delete(user).Error; err != nil {
		return fmt.Errorf("failed to delete user: %w", err)
	}
	return nil
}

func (ur *userRepository) FindByEmail(email string) (*models.User, error) {
	var user models.User
	if err := ur.db.Where("email = ?", email).First(&user).Error; err != nil {
		return nil, fmt.Errorf("failed to find user by email: %w", err)
	}
	return &user, nil
}
