package services

import (
	"log"
	"server/internal/repositories"
)

type userService struct {
	repo repositories.UserRepository
}

func NewUserService(repo repositories.UserRepository) UserService {
	return &userService{
		repo: repo,
	}
}

func (us *userService) GetAllUsers() {
	log.Println("Run get all user")
}

func (us *userService) CreateUser() {
}

func (us *userService) GetUserByID() {
}

func (us *userService) UpdateUser() {
}

func (us *userService) DeleteUser() {
}
