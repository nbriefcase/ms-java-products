package com.personal.eureka.users.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(com.zaxxer.hikari.HikariDataSource.class).build();
    }

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
