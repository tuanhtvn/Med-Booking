package repositories

type userRepository struct {
}

func NewUserRepository() UserRepository {
	return &userRepository{}
}

func (ur *userRepository) FindAll() {

}

func (ur *userRepository) Create() {

}

func (ur *userRepository) FindByID() {

}

func (ur *userRepository) Update() {

}

func (ur *userRepository) Delete() {

}
