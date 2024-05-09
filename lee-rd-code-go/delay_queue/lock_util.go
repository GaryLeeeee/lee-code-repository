package delay_queue

import (
	"errors"
	"github.com/go-redis/redis"
	UUID "github.com/satori/go.uuid"
	"time"
)

type LockUtil struct {
	rd  *redis.Client
	key string
}

func NewLockUtil(r *redis.Client, key string) *LockUtil {
	return &LockUtil{
		rd:  r,
		key: key,
	}
}

func (this *LockUtil) Lock(seconds int) (string, error) {
	uuid := UUID.NewV4().String()
	ret, err := this.rd.Do("SET", this.key, uuid, "EX", seconds, "NX").Result()
	if err != nil || ret != "OK" {
		return "", errors.New("lock fail")
	}
	return uuid, nil
}

func (this *LockUtil) Unlock(uuid string) error {
	script := `if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end`
	ret, err := this.rd.Do("EVAL", script, 1, this.key, uuid).Result()
	if err != nil || ret != 1 {
		return errors.New("unlock fail")
	}
	return nil
}

func (this *LockUtil) RetryLock(seconds int, times int, sleepTime time.Duration) (string, error) {
	for i := 0; i < times; i++ {
		uuid, err := this.Lock(seconds)
		if err != nil {
			time.Sleep(sleepTime)
			continue
		}
		return uuid, nil
	}

	return "", errors.New("retry lock fail")
}
