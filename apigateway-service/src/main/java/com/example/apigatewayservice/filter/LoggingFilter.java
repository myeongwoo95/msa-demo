package com.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter() {
        super(Config.class);
    }

    @Data
    public static class Config {
        private boolean preLogger;
        private boolean postLogger;
    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            // Pre Filter (Pre Filter: 요청을 처리(서비스 호출)하기 전에 실행)
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            if (config.isPreLogger()) {
                log.info("Logging PRE Filter: request id -> {}", request.getId());
            }

            // Post Filter (요청 처리 후, 응답이 클라이언트에게 전달되기 전에 실행)
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostLogger()) {
                    log.info("Logging POST Filter: response code -> {}", response.getStatusCode());
                }
            }));
        }, Ordered.LOWEST_PRECEDENCE);
        // 필터 우선순위 설정
        // Ordered.HIGHEST_PRECEDENCE   (가장 먼저 실행, 글로벌보다도 먼저 실행)
        // Ordered.LOWEST_PRECEDENCE    (가장 나중에)

        return filter;
    }
}
