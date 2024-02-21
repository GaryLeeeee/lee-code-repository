package service

import (
	"context"
	"lee-http-code-go/model"
)

type IUserService interface {
	Ping(ctx context.Context) (string, error)
	// 查询某个user
	GetUser(ctx context.Context, name string) (*model.User, error)
	// 添加user
	AddUser(ctx context.Context, user *model.User) (bool, error)
	// 查询user列表
	ListUser(ctx context.Context) ([]*model.User, error)
}

type UserService struct {
}

func NewUserService() UserService {
	return UserService{}
}

func (UserService) Ping(ctx context.Context) (string, error) {
	return "pong", nil
}

func (UserService) GetUser(ctx context.Context, name string) (*model.User, error) {
	user, ok := users[name]
	if ok {
		return user, nil
	}
	return nil, nil
}

func (UserService) AddUser(ctx context.Context, user *model.User) (bool, error) {
	_, ok := users[user.Name]
	if ok {
		return false, nil
	}
	users[user.Name] = user
	return true, nil
}

func (UserService) ListUser(ctx context.Context) ([]*model.User, error) {
	if len(users) == 0 {
		return nil, nil
	}

	result := make([]*model.User, 0)
	for _, user := range users {
		result = append(result, user)
	}
	return result, nil
}

// ---
var users = make(map[string]*model.User)
