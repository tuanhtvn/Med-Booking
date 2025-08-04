package services

import (
	"errors"
	"log"
	"server/internal/models"
	"server/internal/repositories"
	"server/internal/utils"

	"github.com/jackc/pgx/v5/pgconn"
	"gorm.io/gorm"
)

type userService struct {
	repo repositories.UserRepository
}

func NewUserService(repo repositories.UserRepository) UserService {
	return &userService{
		repo: repo,
	}
}

func (us *userService) GetAllUsers() ([]models.User, error) {
	log.Println("Run get all user")
	return us.repo.FindAll()
}

func (us *userService) CreateUser(user *models.User) error {
	log.Println("Run service create user")
	if err := us.repo.Create(user); err != nil {
		var pgErr *pgconn.PgError
		if errors.As(err, &pgErr) && pgErr.Code == "23505" {
			return utils.WrapError(err, "Email already exist", utils.ErrCodeConFlict)
		}
		return utils.WrapError(err, "Internal Server Error", utils.ErrCodeInternal)
	}
	return nil
}

func (us *userService) GetUserByID(id int) (*models.User, error) {
	data, err := us.repo.FindByID(id)
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, utils.WrapError(err, "user not found", utils.ErrCodeNotFound)
		}
		return nil, err

	}
	return data, nil
}

func (us *userService) UpdateUser() {
}

func (us *userService) DeleteUser() {
}
