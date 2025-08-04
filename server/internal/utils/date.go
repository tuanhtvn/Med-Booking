package utils

import (
	"database/sql/driver"
	"errors"
	"fmt"
	"time"
)

type Date time.Time

// JSON -> Struct
func (d *Date) UnmarshalJSON(b []byte) error {
	s := string(b)
	if len(s) < 2 {
		return fmt.Errorf("invalid date")
	}
	s = s[1 : len(s)-1]
	t, err := time.Parse("2006-01-02", s)
	if err != nil {
		return fmt.Errorf("failed to parse date: %w", err)
	}
	*d = Date(t)
	return nil
}

// Struct -> DB
func (d *Date) Value() (driver.Value, error) {
	return time.Time(*d), nil
}

// DB -> Struct
func (d *Date) Scan(value interface{}) error {
	if value == nil {
		d = nil
		return nil
	}

	v, ok := value.(time.Time)
	if !ok {
		return errors.New("can't convert value to Time")
	}
	*d = Date(v)
	return nil
}

// Struct -> JSON
func (d *Date) MarshalJSON() ([]byte, error) {
	if d == nil {
		return nil, nil
	}
	return []byte(fmt.Sprintf(`"%s"`, time.Time(*d).Format("2006-01-02"))), nil
}
