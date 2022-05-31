package com.personal.eureka.oauth2.security.event;

import com.personal.eureka.commons.users.models.entity.User;
import com.personal.eureka.oauth2.service.IUserService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationHandler implements AuthenticationEventPublisher {

    @Autowired
    private IUserService userService;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            return;
        }
        UserDetails user = (UserDetails) authentication.getDetails();
        log.info("Login success!. " + user.getUsername());

        User byUsername = userService.findByUsername(authentication.getName());
        if (byUsername.getFailedAttempts() != null && byUsername.getFailedAttempts() > 0) {
            byUsername.setFailedAttempts(0);
            userService.update(byUsername, byUsername.getUserId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        log.warn("Login failed!.");
        try {
            User byUsername = userService.findByUsername(authentication.getName());
            if (byUsername.getFailedAttempts() == null) {
                byUsername.setFailedAttempts(0);
            }

            byUsername.setFailedAttempts(byUsername.getFailedAttempts() + 1);
            log.info(String.format("User \"%s\" attempts: %d", byUsername.getUsername(), byUsername.getFailedAttempts()));

            if (byUsername.getFailedAttempts() >= 3) {
                byUsername.setIsActive(false);
                log.error(String.format("User \"%s\" was inactivated!", authentication.getName()));
            }

            userService.update(byUsername, byUsername.getUserId());

        } catch (FeignException ex) {
            log.error(String.format("User \"%s\" not found!", authentication.getName()));
        }
    }
}
