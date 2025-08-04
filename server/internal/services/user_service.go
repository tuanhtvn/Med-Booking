package services

import (
	"errors"
	"log"
	"server/internal/models"
	"server/internal/repositories"
	"server/internal/utils"

	"github.com/jackc/pgx/v5/pgconn"
	"golang.org/x/crypto/bcrypt"
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

	user.Email = utils.NormalizeString(user.Email)
	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(user.Password), bcrypt.DefaultCost)

	if err != nil {
		return utils.WrapError(err, "Failed to hash password", utils.ErrCodeInternal)
	}

	user.Password = string(hashedPassword)

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

func (us *userService) UpdateUser(id int, updateUser *models.User) error {
	log.Println("Run service update user")
	updateUser.Email = utils.NormalizeString(updateUser.Email)

	currentUser, err := us.GetUserByID(id)
	if err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return utils.WrapError(err, "user not found", utils.ErrCodeNotFound)
		}
		return err
	}

	currentUser.Fullname = updateUser.Fullname
	currentUser.Email = updateUser.Email
	currentUser.Gender = updateUser.Gender
	currentUser.Birthday = updateUser.Birthday
	currentUser.Role = updateUser.Role

	if updateUser.Password != "" {
		hashedPassword, err := bcrypt.GenerateFromPassword([]byte(updateUser.Password), bcrypt.DefaultCost)
		if err != nil {
			return utils.WrapError(err, "Failed to hash password", utils.ErrCodeInternal)
		}
		currentUser.Password = string(hashedPassword)
	}

	if currentUser.Email != updateUser.Email {
		existingUser, err := us.repo.FindByEmail(updateUser.Email)
		if err != nil && !errors.Is(err, gorm.ErrRecordNotFound) {
			return utils.WrapError(err, "Internal Server Error", utils.ErrCodeInternal)
		}
		if existingUser != nil && existingUser.Id != currentUser.Id {
			return utils.WrapError(err, "Email already exists", utils.ErrCodeConFlict)
		}
	}

	if err := us.repo.Update(currentUser); err != nil {
		if errors.Is(err, gorm.ErrRecordNotFound) {
			return utils.WrapError(err, "user not found", utils.ErrCodeNotFound)
		}
		var pgErr *pgconn.PgError
		if errors.As(err, &pgErr) && pgErr.Code == "23505" {
			return utils.WrapError(err, "Email already exist", utils.ErrCodeConFlict)
		}
		return utils.WrapError(err, "Internal Server Error", utils.ErrCodeInternal)
	}
	*updateUser = *currentUser
	log.Println("Update user successfully")
	return nil
}

func (us *userService) DeleteUser(id int) error {
	log.Println("Run service delete user")
	user, err := us.GetUserByID(id)
	if err != nil {
		return err
	}

	if err := us.repo.Delete(user); err != nil {
		return utils.WrapError(err, "Internal Server Error", utils.ErrCodeInternal)
	}
	log.Println("Delete user successfully")
	return nil
}
