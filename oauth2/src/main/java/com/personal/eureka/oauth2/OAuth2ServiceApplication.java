package com.personal.eureka.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class OAuth2ServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ServiceApplication.class, args);
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info(passwordEncoder.encode("12345"));
        log.info(passwordEncoder.encode("12345"));
        log.info(passwordEncoder.encode("12345"));
        log.info(passwordEncoder.encode("12345"));
    }
}
