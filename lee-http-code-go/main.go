package main

import (
	"context"
	"fmt"
	"lee-http-code-go/endpoint"
	"lee-http-code-go/service"
	"lee-http-code-go/transport"
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"
)

func main() {
	// 创建上下文信息
	ctx := context.Background()
	// 创建service
	svc := service.NewUserService()
	// 创建错误通道
	errChan := make(chan error)

	// 监听ctrl+c kill
	go func() {
		s := make(chan os.Signal, 1)
		signal.Notify(s, syscall.SIGINT, syscall.SIGTERM)
		errChan <- fmt.Errorf("%s", <-s)
	}()

	// 映射端点
	endpoints := endpoint.Endpoints{
		PingEndpoint:     endpoint.MakePingEndpoint(svc),
		GetUserEndpoint:  endpoint.MakeGetUserEndpoint(svc),
		AddUserEndpoint:  endpoint.MakeAddUserEndpoint(svc),
		ListUserEndpoint: endpoint.MakeListUserEndpoint(svc),
	}

	// http传输
	go func() {
		handler := transport.NewHTTPServer(ctx, endpoints)
		errChan <- http.ListenAndServe(":8080", handler)
	}()

	log.Fatalln(<-errChan)
}
