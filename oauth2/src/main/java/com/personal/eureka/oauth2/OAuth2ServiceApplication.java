package com.personal.eureka.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class OAuth2ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ServiceApplication.class, args);
    }

}
