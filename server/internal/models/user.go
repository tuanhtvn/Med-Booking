package models

import (
	"server/internal/utils"
)

type User struct {
	Id        int         `json:"user_id" gorm:"column:user_id;primaryKey"`
	Fullname  string      `json:"fullname" binding:"required"`
	Email     string      `json:"email" binding:"required,email"`
	Password  string      `json:"password,omitempty" binding:"required,min=8"`
	Role      string      `json:"role" binding:"required"`
	Gender    string      `json:"gender" binding:"required"`
	Birthday  *utils.Date `json:"birthday,omitempty" binding:"required"`
	CreatedAt *utils.Date `json:"created_at,omitempty" gorm:"autoCreateTime"`
	UpdatedAt *utils.Date `json:"update_at,omitempty"  gorm:"autoUpdateTime"`
}
