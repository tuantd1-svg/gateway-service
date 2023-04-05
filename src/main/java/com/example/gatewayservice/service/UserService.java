package com.example.gatewayservice.service;

import com.example.commonapi.model.ResultMessage;
import com.example.gatewayservice.model.UserRDto;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<ResultMessage> addNewUser(UserRDto userRDto);
}
