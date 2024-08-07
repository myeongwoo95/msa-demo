package com.example.apigatewayservice.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    @Value("${token.secret}")
    private String jwtSecret;

    public AuthFilter() {
        super(Config.class);
    }

    public static class Config {
        // Put the configuration properties
    }

    @Override
    public GatewayFilter apply(Config config) {
        // Pre Filter (Pre Filter: 요청을 처리(서비스 호출)하기 전에 실행)
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Auth PRE filter : request id -> {}", request.getId());

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String jwt = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            jwt = jwt.replace("Bearer ", "");

            if(!isJwtValid(jwt)) {
                return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);
            }

            // Post Filter (요청 처리 후, 응답이 클라이언트에게 전달되기 전에 실행)
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Auth POST filter : response code -> {}", response.getStatusCode());
            }));
        };
    }

    // 토큰 검증
    private boolean isJwtValid(String token) {
        boolean returnValue = true;

        try {
            Claims claims = getClaimsFromToken(token);
            String userId = claims.getSubject();

            if (userId == null || userId.isEmpty()) {
                returnValue = false;
            }
        } catch (SignatureException e) {
            // Invalid JWT signature
            e.printStackTrace();
            returnValue = false;
        }

        return returnValue;
    }

    // 에러 메세지 반환
    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus unauthorized) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(unauthorized);
        log.error(errorMessage);
        return response.setComplete();
    }

    // JWT > Claims 반환
    private Claims getClaimsFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

