package com.example.gatewayservice.filter;

import com.example.commonapi.parameter.enumable.EPermission;
import com.example.gatewayservice.model.VerifyPermission;
import com.example.gatewayservice.model.VerifyToken;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {


    private final WebClient.Builder webClientBuilder;

    public static final String X_B3_Trace_ID ="X-B3-TraceId";


    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token != null) {
                VerifyToken verifyToken = new VerifyToken();
                verifyToken.setUri(exchange.getRequest().getURI().getPath());
                return webClientBuilder.build()
                        .post()
                        .uri("http://localhost:9000/private/authentication-service/user/verify-token")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .header(X_B3_Trace_ID, UUID.randomUUID().toString())
                        .body(Mono.just(verifyToken),VerifyToken.class)
                        .retrieve()
                        .bodyToMono(VerifyPermission.class)
                        .flatMap(isValid -> {
                            if (isValid.getVerifyRole().equals(EPermission.ACCEPT)) {
                                return chain.filter(exchange);
                            } else {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();
                            }
                        });
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }
    public static class Config {
    }
}
