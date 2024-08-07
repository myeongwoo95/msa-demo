package com.example.apigatewayservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.WebFilter;

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

        // JwtAuthenticationFilter 클래스가 WebFilter를 구현하고 있기 때문에 Spring Security는 필터를 자동으로 감지하고 적용한다.
//        http.addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        http
            .authorizeExchange(exchanges -> exchanges
                    .pathMatchers(HttpMethod.GET, "/user-service/**").hasRole("USER")
                    .anyExchange().authenticated()
            );

        return http.build();
    }
}
