package com.personal.eureka.oauth2.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {

    @Autowired
    private Environment env;

//    @Bean
//    public CachingConnectionFactory defaultConnectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setAddresses(env.getProperty("ms.rabbitmq.host", "localhost"));
//        connectionFactory.setPort(Integer.parseInt(env.getProperty("ms.rabbitmq.port", "5672")));
//        connectionFactory.setUsername(env.getProperty("ms.rabbitmq.user", "user"));
//        connectionFactory.setPassword(env.getProperty("ms.rabbitmq.password", "12345"));
//        return connectionFactory;
//    }
}
