package com.example.apigatewayservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Slf4j
@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Value("${token.secret}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        log.info("JwtAuthenticationFilter ==========");

        if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return Mono.empty();
        }

        String jwt = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
        jwt = jwt.replace("Bearer ", "");

        Claims claims = getClaimsFromToken(jwt);
        String userId = claims.getSubject();

        if (userId != null && !userId.isEmpty()) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                    .doFinally(signalType -> {
                        log.info("Authentication completed for user: {}", userId);
                    });
        }

        return Mono.empty(); // 인증 실패 시 빈 Mono 반환
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
