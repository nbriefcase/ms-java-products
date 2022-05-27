package com.personal.eureka.items.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    private RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
