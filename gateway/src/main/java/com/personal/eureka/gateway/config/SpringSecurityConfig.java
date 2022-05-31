package com.personal.eureka.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

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
                .and().addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                //.and().cors().configurationSource(corsConfigurationSource())
                .csrf().disable()
                .build();
    }
/*
    // NO reactive configuration to allow CORS...

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("*"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
    */
}
