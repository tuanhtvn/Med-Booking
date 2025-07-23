package services

type UserService interface {
	GetAllUsers()
	CreateUser()
	GetUserByID()
	UpdateUser()
	DeleteUser()
}
