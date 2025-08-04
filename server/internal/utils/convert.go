package utils

import "strings"

func NormalizeString(text string) string {
	// Convert to lowercase
	text = strings.ToLower(text)
	// Trim whitespace
	text = strings.TrimSpace(text)
	return text
}
