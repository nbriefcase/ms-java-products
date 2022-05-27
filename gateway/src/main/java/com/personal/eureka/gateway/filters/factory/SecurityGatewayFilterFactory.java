package com.personal.eureka.gateway.filters.factory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
public class SecurityGatewayFilterFactory extends AbstractGatewayFilterFactory<SecurityGatewayFilterFactory.Configuration> {

    public SecurityGatewayFilterFactory() {
        super(Configuration.class);
    }

    @Override
    public GatewayFilter apply(Configuration config) {
        return (exchange, chain) -> {
            log.info("Executing pre filter factory: " + config.message);
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                Optional.ofNullable(config.cookieValue)
                        .ifPresent(value -> exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, value).build()));
                log.info("Executing post filter factory: " + config.message);
            }));
        };
    }

    @Data
    public static class Configuration {
        private String message;
        private String cookieName;
        private String cookieValue;
    }
}
