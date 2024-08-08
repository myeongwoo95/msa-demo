package com.example.apigatewayservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
        http.csrf(csrf -> csrf.disable());
        http.cors((cors) -> cors.disable());
        http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance()); //session STATELESS

        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        http.logout(ServerHttpSecurity.LogoutSpec::disable);

        // JWT 인증 필터 추가
        http.addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        http
            .authorizeExchange(exchanges -> exchanges
                    // 카탈로그 서비스
                    .pathMatchers(HttpMethod.GET, "/catalog-service/catalogs").hasAnyRole("ADMIN", "MANAGER", "USER", "GUEST")

                    // 유저 서비스
                    .pathMatchers(HttpMethod.POST, "/user-service/login").permitAll()
                    .pathMatchers(HttpMethod.POST, "/user-service/users").permitAll()
                    .pathMatchers(HttpMethod.GET, "/user-service/users").hasAnyRole("ADMIN", "MANAGER", "USER")
                    .pathMatchers(HttpMethod.GET, "/user-service/users/**").hasAnyRole("ADMIN", "MANAGER", "USER")

                    // 오더 서비스
                    .pathMatchers(HttpMethod.POST, "/order-service/*/orders").hasAnyRole("ADMIN", "MANAGER", "USER")
                    .pathMatchers(HttpMethod.GET, "/order-service/*/orders").hasAnyRole("ADMIN", "MANAGER", "USER")

                    .anyExchange().denyAll()
            );

        return http.build();
    }
}
