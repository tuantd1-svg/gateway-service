package com.example.gatewayservice.service.impl;

import com.example.commonapi.model.ResultMessage;
import com.example.gatewayservice.model.UserRDto;
import com.example.gatewayservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.example.gatewayservice.filter.JwtAuthenticationFilter.X_B3_Trace_ID;

@Service
public class UserServiceImpl implements UserService {
    @Value("${url.api.core.account}")
    private String url;

    @Autowired
    private  WebClient.Builder webClientBuilder;
    @Override
    public Mono<ResultMessage> addNewUser(UserRDto userRDto) {
        return webClientBuilder.baseUrl(url).build()
                .post()
                .uri("/api/createNewUser")
                .header(X_B3_Trace_ID, UUID.randomUUID().toString())
                .body(Mono.just(userRDto), UserRDto.class)
                .retrieve()
                .bodyToMono(ResultMessage.class);
    }
}
