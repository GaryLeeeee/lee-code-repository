### 基于Go kit+Gin开发一个Web项目Demo
#### 1、环境准备
* Go kit：`go get github.com/go-kit/kit`
* Gin：`go get github.com/gin-gonic/gin`

#### 2、目录结构
Go kit采用三层架构方式
* `endpoint`：负责接受请求参数并返回响应结果
* `service`：负责定义业务接口并实现接口方法
* `transport`：负责对外提供调用接口(http/rpc)

### 3、如何启动？
* 方式1：终端执行`go run main.go`
* 方式2: 在IDE上点击`main.go`，在`main()`上点击`run/debug`

### 4、测试服务是否正常启动
1. 终端打印`Listening and serving HTTP on :8080`
2. 请求`http://localhost:8080/ping` ，返回`pong`
```json
{
  "code": 0,
  "message": "success",
  "data": "pong"
}
```