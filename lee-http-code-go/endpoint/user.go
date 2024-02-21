package endpoint

import (
	"context"
	"github.com/go-kit/kit/endpoint"
	"lee-http-code-go/model"
	"lee-http-code-go/service"
)

type Endpoints struct {
	PingEndpoint     endpoint.Endpoint
	GetUserEndpoint  endpoint.Endpoint
	AddUserEndpoint  endpoint.Endpoint
	ListUserEndpoint endpoint.Endpoint
}

func MakePingEndpoint(svc service.IUserService) endpoint.Endpoint {
	return func(ctx context.Context, request interface{}) (interface{}, error) {
		_ = request.(model.PingRequest)
		res, err := svc.Ping(ctx)
		if err != nil {
			return model.PingResponse{
				Code:    500,
				Message: err.Error(),
			}, err
		}
		return model.PingResponse{
			Code:    0,
			Message: "success",
			Data:    res,
		}, nil
	}
}

func MakeGetUserEndpoint(svc service.IUserService) endpoint.Endpoint {
	return func(ctx context.Context, request interface{}) (interface{}, error) {
		req := request.(model.GetUserRequest)
		res, err := svc.GetUser(ctx, req.Name)
		if err != nil {
			return model.GetUserResponse{
				Code:    500,
				Message: err.Error(),
			}, err
		}
		return model.GetUserResponse{
			Code:    0,
			Message: "success",
			Data:    res,
		}, nil
	}
}

func MakeAddUserEndpoint(svc service.IUserService) endpoint.Endpoint {
	return func(ctx context.Context, request interface{}) (interface{}, error) {
		req := request.(model.AddUserRequest)
		res, err := svc.AddUser(ctx, &model.User{
			Name: req.Name,
			Age:  req.Age,
		})
		if err != nil {
			return model.AddUserResponse{
				Code:    500,
				Message: err.Error(),
			}, err
		}
		return model.AddUserResponse{
			Code:    0,
			Message: "success",
			Data:    res,
		}, nil
	}
}

func MakeListUserEndpoint(svc service.IUserService) endpoint.Endpoint {
	return func(ctx context.Context, request interface{}) (interface{}, error) {
		_ = request.(model.ListUserRequest)
		res, err := svc.ListUser(ctx)
		if err != nil {
			return model.ListUserResponse{
				Code:    500,
				Message: err.Error(),
			}, err
		}
		return model.ListUserResponse{
			Code:    0,
			Message: "success",
			Data:    res,
		}, nil
	}
}
