/*
 *  Author : Hanamagouda Goudar
 *  Date : 16-11-2023
 *  Created with : IntelliJ IDEA Community Edition
 */

package com.pesto.APIGateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/api/v1/**")
                        .uri("http://localhost:8081/"))
                .route(p -> p
                        .path("/api/v2/**")
                        .uri("http://localhost:8080/"))
                .build();
    }
}
