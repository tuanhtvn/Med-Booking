package configs

import (
	"fmt"
	"server/internal/utils"
)

type ServerConfig struct {
	Port        string
	Environment string
	Debug       bool
	ConnString  string
}

type Config struct {
	Server ServerConfig
}

func NewConfig() *Config {
	return &Config{
		Server: ServerConfig{
			Port:        utils.GetEnv("APP_PORT", "8080"),
			Environment: utils.GetEnv("APP_ENV", "development"),
			Debug:       utils.GetEnv("APP_DEBUG", "false") == "true",
			ConnString:  utils.GetEnv("CONN_STRING", ""),
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
