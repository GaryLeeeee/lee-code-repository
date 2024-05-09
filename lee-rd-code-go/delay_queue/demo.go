package delay_queue

import (
	"fmt"
	"lee-redis-code-go/lee-rd-code-go/client"
	"strings"
	"time"
)

const (
	LEE_TASK_QUEUE_KEY = "lee_task_queue"
	LEE_TASK_LOCK_KEY  = "lee_task_lock"
)

var delayQueue *LeeDelayQueue

func getDelayQueueInstance() *LeeDelayQueue {
	if delayQueue == nil {
		delayQueue = NewLeeDelayQueue(client.GetRedisClient(), LEE_TASK_QUEUE_KEY, LEE_TASK_LOCK_KEY)
	}
	return delayQueue
}

func AddTask(uid string, name string) {
	err := getDelayQueueInstance().Push(MemberWithScore{
		Member: fmt.Sprintf("%s:%s", uid, name),
		Score:  float64(time.Now().Unix()),
	})
	if err != nil {
		// err log
	}
}

func ConsumeTask() {
	consumer := NewDelayQueueConsumer(getDelayQueueInstance())
	consumer.Consume(ConsumeSth)
}

func ConsumeSth(item interface{}) {
	member, ok := item.(MemberWithScore)
	if !ok {
		// err log
		return
	}
	data := strings.Split(member.Member, ":")
	uid := data[0]
	name := data[1]
	fmt.Printf("ConsumeSth | uid:%s,name:%s\n", uid, name)
}
