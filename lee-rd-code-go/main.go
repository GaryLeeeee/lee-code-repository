package main

import (
	"fmt"
	"lee-redis-code-go/lee-rd-code-go/client"
	"lee-redis-code-go/lee-rd-code-go/delay_queue"
)

func main() {
	// redis
	client.InitRedis()
	// 延迟队列
	go delay_queue.ConsumeTask()
	for i := 0; i < 10; i++ {
		delay_queue.AddTask(fmt.Sprintf("uid%d", i), fmt.Sprintf("name%d", i))
	}
	select {}
}
