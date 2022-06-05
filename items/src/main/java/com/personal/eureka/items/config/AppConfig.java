package com.personal.eureka.items.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * This Bean will be use with CircuitBreakerFactory class, It wont work with annotations
     *
     * @return Customizer bean for the CircuitBreakerFactory
     */
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id ->
                new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(CircuitBreakerConfig.custom()
                                .slidingWindowSize(10)
                                .failureRateThreshold(50)
                                .waitDurationInOpenState(Duration.ofSeconds(10L))
                                .permittedNumberOfCallsInHalfOpenState(5)
                                .slowCallRateThreshold(50)
                                .slowCallDurationThreshold(Duration.ofSeconds(2L))
                                .build())
                        .timeLimiterConfig(TimeLimiterConfig.custom()
                                .timeoutDuration(Duration.ofSeconds(6L))
                                .build())
                        .build()
        );
    }

    @Autowired
    private Environment env;

    @Bean
    public CachingConnectionFactory defaultConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(env.getProperty("ms.rabbitmq.host", "localhost"));
        connectionFactory.setPort(Integer.parseInt(env.getProperty("ms.rabbitmq.port", "5672")));
        connectionFactory.setUsername(env.getProperty("ms.rabbitmq.user", "user"));
        connectionFactory.setPassword(env.getProperty("ms.rabbitmq.password", "12345"));
        return connectionFactory;
    }
}
