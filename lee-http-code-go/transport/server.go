package transport

import (
	"context"
	"github.com/gin-gonic/gin"
	httptransport "github.com/go-kit/kit/transport/http"
	"lee-http-code-go/endpoint"
	"net/http"
)

func NewHTTPServer(ctx context.Context, endpoints endpoint.Endpoints) http.Handler {
	// 初始化gin
	r := gin.Default()
	// 添加路由
	r.Any("ping", gin.WrapH(
		httptransport.NewServer(endpoints.PingEndpoint,
			decodePingRequest,
			encodeResponse),
	))
	r.Any("get_user", gin.WrapH(
		httptransport.NewServer(endpoints.GetUserEndpoint,
			decodeGetUserRequest,
			encodeResponse),
	))
	r.Any("add_user", gin.WrapH(
		httptransport.NewServer(endpoints.AddUserEndpoint,
			decodeAddUserRequest,
			encodeResponse),
	))
	r.Any("list_user", gin.WrapH(
		httptransport.NewServer(endpoints.ListUserEndpoint,
			decodeListUserRequest,
			encodeResponse),
	))
	// 启动gin
	_ = r.Run()
	return r
}
