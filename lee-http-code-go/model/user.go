package model

type User struct {
	Name string `json:"name"`
	Age  int    `json:"age"`
}

//---

type PingRequest struct {
}
type PingResponse struct {
	Code    int    `json:"code"`
	Message string `json:"message"`
	Data    string `json:"data"`
}

// 查询某个User
type GetUserRequest struct {
	Name string `json:"name"`
}
type GetUserResponse struct {
	Code    int    `json:"code"`
	Message string `json:"message"`
	Data    *User  `json:"data"`
}

// 添加user
type AddUserRequest struct {
	Name string `json:"name"`
	Age  int    `json:"age"`
}
type AddUserResponse struct {
	Code    int    `json:"code"`
	Message string `json:"message"`
	Data    bool   `json:"data"`
}

// 查询user列表
type ListUserRequest struct {
}
type ListUserResponse struct {
	Code    int     `json:"code"`
	Message string  `json:"message"`
	Data    []*User `json:"data"`
}
