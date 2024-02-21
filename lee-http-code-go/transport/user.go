package transport

import (
	"context"
	"encoding/json"
	"lee-http-code-go/model"
	"net/http"
)

func decodePingRequest(ctx context.Context, request *http.Request) (interface{}, error) {
	var req model.PingRequest
	return req, nil
}

func decodeGetUserRequest(ctx context.Context, request *http.Request) (interface{}, error) {
	var req model.GetUserRequest
	err := json.NewDecoder(request.Body).Decode(&req)
	if err != nil {
		return nil, err
	}
	return req, nil
}

func decodeAddUserRequest(ctx context.Context, request *http.Request) (interface{}, error) {
	var req model.AddUserRequest
	err := json.NewDecoder(request.Body).Decode(&req)
	if err != nil {
		return nil, err
	}
	return req, nil
}

func decodeListUserRequest(ctx context.Context, request *http.Request) (interface{}, error) {
	var req model.ListUserRequest
	return req, nil
}

func encodeResponse(ctx context.Context, rw http.ResponseWriter, response interface{}) error {
	rw.Header().Set("Content-Type", "application/json")
	return json.NewEncoder(rw).Encode(response)
}
