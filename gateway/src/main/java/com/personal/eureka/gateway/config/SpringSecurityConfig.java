package com.personal.eureka.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http.authorizeExchange()
                .pathMatchers("/api/security/oauth/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/users/users/{id}").hasAnyRole("ADMIN", "USER")
                .pathMatchers(HttpMethod.GET,
                        "/api/products/products",
                        "/api/items/items",
                        "/api/users/users",
                        "/api/items/item/{id}/amount/{amount}",
                        "/api/products/product/{id}").permitAll()
                .pathMatchers(
                        "/api/products/**",
                        "/api/items/**",
                        "/api/users/users/").hasAnyRole("ADMIN")
                .anyExchange().authenticated()
                .and().csrf().disable()
                .build();
    }
}
