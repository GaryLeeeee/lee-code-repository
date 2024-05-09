package delay_queue

import "time"

type DelayQueue interface {
	GetCount() (int64, error)
	RetryLock(seconds int, times int, sleepTime time.Duration) (string, error)
	Peek(num int64) ([]MemberWithScore, error)
	Unlock(uuid string) error
	MultiDel(keys []interface{}) error
	Push(member MemberWithScore) error
}

type DelayQueueConsumer struct {
	Queue   DelayQueue
	PeekNum int64
}

func NewDelayQueueConsumer(queue DelayQueue) *DelayQueueConsumer {
	return &DelayQueueConsumer{
		Queue:   queue,
		PeekNum: 10,
	}
}

func (this *DelayQueueConsumer) Consume(fc func(item interface{})) {
	ch := make(chan interface{})
	go func() {
		for {
			// 通道监听
			s := <-ch
			fc(s)
		}
	}()

	for {
		time.Sleep(1 * time.Second)
		// 查询是否有可消费消息
		size, _ := this.Queue.GetCount()
		if size == 0 {
			continue
		}
		// 分布式锁
		uuid, err := this.Queue.RetryLock(5, 3, 1*time.Second)
		if err != nil {
			// err log
			continue
		}
		// peek
		members, err := this.Queue.Peek(this.PeekNum)
		if err != nil {
			// err log
			_ = this.Queue.Unlock(uuid)
			continue
		}
		if len(members) == 0 {
			_ = this.Queue.Unlock(uuid)
			continue
		}
		// 取到时的消息
		now := time.Now().Unix()
		needProcessMembers := make([]MemberWithScore, 0)
		for _, member := range members {
			if int64(member.Score) <= now {
				needProcessMembers = append(needProcessMembers, member)
				continue
			}
			break
		}
		if len(needProcessMembers) == 0 {
			_ = this.Queue.Unlock(uuid)
			continue
		}
		// 取出消费
		keys := make([]interface{}, 0, len(needProcessMembers))
		for _, needProcessMember := range needProcessMembers {
			keys = append(keys, needProcessMember.Member)
		}
		if err = this.Queue.MultiDel(keys); err != nil {
			// err log
			_ = this.Queue.Unlock(uuid)
			continue
		}
		// 消费
		_ = this.Queue.Unlock(uuid)
		for _, needProcessMember := range needProcessMembers {
			ch <- needProcessMember
		}
	}
}
