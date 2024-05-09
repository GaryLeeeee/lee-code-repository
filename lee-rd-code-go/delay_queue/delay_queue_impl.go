package delay_queue

import "github.com/go-redis/redis"

type LeeDelayQueue struct {
	*LockUtil
	*SortedsetUtil
}

func NewLeeDelayQueue(rd *redis.Client, queueKey, lockKey string) *LeeDelayQueue {
	return &LeeDelayQueue{
		LockUtil:      NewLockUtil(rd, lockKey),
		SortedsetUtil: NewSortedsetUtil(rd, queueKey),
	}
}
