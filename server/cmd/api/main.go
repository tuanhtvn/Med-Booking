package main

import (
	"server/internal/app"
	"server/internal/configs"
)

func main() {
	// Init config
	cfg := configs.NewConfig()
	// Init App
	application := app.NewApplication(cfg)
	// Start Server
	if err := application.Run(); err != nil {
		panic(err)
	}

}
