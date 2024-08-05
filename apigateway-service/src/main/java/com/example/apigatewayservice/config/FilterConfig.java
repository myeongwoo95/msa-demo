package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {

//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/catalog-service/**")
                        .filters(f ->f.addRequestHeader("catalog-first-request", "catalog-first-response")
                                .addResponseHeader("catalog-first-response", "catalog-first-response"))
                        .uri("http://localhost:8080")
                ).route(r -> r.path("/order-service/**")
                        .filters(f ->f.addRequestHeader("order-first-request", "order-first-response")
                                .addResponseHeader("order-first-response", "order-first-response"))
                        .uri("http://localhost:8081")
                ).route(r -> r.path("/user-service/**")
                        .filters(f ->f.addRequestHeader("user-first-request", "user-first-response")
                                .addResponseHeader("user-first-response", "user-first-response"))
                        .uri("http://localhost:8082")
                ).build();
    }
}
