package configs

import (
	"fmt"
	"server/internal/utils"
)

type ServerConfig struct {
	Port          string
	Environment   string
	Debug         bool
	ConnString    string
	RateRequest   string
	RateBrust     string
	RateTimeCheck string
}

type Config struct {
	Server ServerConfig
}

func NewConfig() *Config {
	return &Config{
		Server: ServerConfig{
			Port:          utils.GetEnv("APP_PORT", ":8080"),
			Environment:   utils.GetEnv("APP_ENV", "development"),
			Debug:         utils.GetEnv("APP_DEBUG", "false") == "true",
			ConnString:    utils.GetEnv("CONN_STRING", ""),
			RateRequest:   utils.GetEnv("RATE_LIMITER_REQUEST", "5"),
			RateBrust:     utils.GetEnv("RATE_LIMITER_BRUST", "15"),
			RateTimeCheck: utils.GetEnv("RATE_LIMITER_TIME_CHECK", "3"),
		},
	}
}

func (c *Config) ToString() string {
	return fmt.Sprintf("Port=%s environment=%s debug=%s",
		c.Server.Port,
		c.Server.Environment,
		fmt.Sprintf("%t", c.Server.Debug),
	)
}
