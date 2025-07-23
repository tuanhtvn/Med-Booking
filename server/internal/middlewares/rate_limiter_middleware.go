package middlewares

import (
	"net/http"
	"sync"
	"time"

	"github.com/gin-gonic/gin"
	"golang.org/x/time/rate"
)

type Client struct {
	limiter  *rate.Limiter
	lastSeen time.Time
}

var (
	clients = make(map[string]*Client)
	mu      sync.Mutex
)

func getClientIP(ctx *gin.Context) string {
	ip := ctx.ClientIP()
	if ip == "" {
		ip = ctx.Request.RemoteAddr
	}
	return ip
}

func getRateLimiter(ip string, resquest, brust int) *rate.Limiter {
	mu.Lock()
	defer mu.Unlock()

	client, exists := clients[ip]
	if !exists {
		limiter := rate.NewLimiter(rate.Limit(resquest), brust) // 5 request/sec, brust 10 ==> ngân sách request (có sẵn) là 10, nếu ngân sách < 10 thì sau 1s cấp lại tối đa 5 request cho đến khi đủ 10
		newClient := &Client{limiter, time.Now()}
		clients[ip] = newClient
		return limiter
	}

	client.lastSeen = time.Now()
	return client.limiter
}

func CleanupClient(timeCheck int) {
	for {
		time.Sleep(time.Minute)
		mu.Lock()
		for ip, client := range clients {
			if time.Since(client.lastSeen) > time.Duration(timeCheck)*time.Minute {
				delete(clients, ip)
			}
		}
		mu.Unlock()
	}
}

func RateLimitingMiddleware(request, brust int) gin.HandlerFunc {
	return func(ctx *gin.Context) {
		ip := getClientIP(ctx)
		limiter := getRateLimiter(ip, request, brust)

		if !limiter.Allow() {
			ctx.AbortWithStatusJSON(http.StatusTooManyRequests, gin.H{
				"error":   "Too many request",
				"message": "Bạn đã gửi quá nhiều request. Vui lòng, hãy thử lại sau!",
			})
			return
		}

		ctx.Next()
	}
}
