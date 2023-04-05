package com.example.gatewayservice.logger;

import ch.qos.logback.classic.ClassicConstants;
import com.example.loggerapi.utils.LoggerUtils2;
import com.example.loggerapi.wrapper.MyHttpServletReponseWrapper;
import com.example.loggerapi.wrapper.MyHttpServletRequestWrapper;
import lombok.SneakyThrows;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;


@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    public static final String X_B3_Trace_ID ="X-B3-TraceId";


    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String traceId = UUID.randomUUID().toString();
        request = request.mutate().header(X_B3_Trace_ID, traceId).build();
        MDC.put(X_B3_Trace_ID,traceId);
        return chain.filter(exchange.mutate().request(request).build());

    }
    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

}