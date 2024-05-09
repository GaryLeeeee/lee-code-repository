package client

import "github.com/go-redis/redis"

var _rd *redis.Client

func InitRedis() {
	client := redis.NewClient(&redis.Options{
		Addr:     "127.0.0.1:6379",
		Password: "",
	})
	_rd = client
}

func GetRedisClient() *redis.Client {
	return _rd
}
