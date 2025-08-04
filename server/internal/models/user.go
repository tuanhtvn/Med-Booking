package models

import (
	"server/internal/utils"
)

type User struct {
	Id        int         `json:"user_id" gorm:"column:user_id;primaryKey"`
	Fullname  string      `json:"fullname"`
	Email     string      `json:"email"`
	Password  string      `json:"password"`
	Role      string      `json:"role"`
	Gender    string      `json:"gender"`
	Birthday  *utils.Date `json:"birthday"`
	CreatedAt *utils.Date `json:"created_at"`
	UpdatedAt *utils.Date `json:"update_at"`
}
