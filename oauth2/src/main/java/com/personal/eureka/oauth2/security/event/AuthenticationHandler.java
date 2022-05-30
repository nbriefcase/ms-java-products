package com.personal.eureka.oauth2.security.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationHandler implements AuthenticationEventPublisher {

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            return;
        }
        UserDetails user = (UserDetails) authentication.getDetails();
        log.info("Login success!. " + user.getUsername());
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        log.warn("Login failed!.");
    }
}
