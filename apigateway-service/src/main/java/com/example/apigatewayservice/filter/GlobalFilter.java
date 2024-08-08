package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// 모든 필터의 가장 첫번째에 시작이 되고 마지막에 종료가 된다.
@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public GlobalFilter() {
        super(Config.class);
    }

    @Data
    public static class Config {
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Pre Filter (Pre Filter: 요청을 처리(서비스 호출)하기 전에 실행)
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (config.isPreLogger()) {
                log.info("Global Filter Start: request id -> {}", request.getId());
            }

            // Post Filter (요청 처리 후, 응답이 클라이언트에게 전달되기 전에 실행)
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()){
                    log.info("Global filter End: response code -> {}", response.getStatusCode());
                }
            }));
        };
    }
}

