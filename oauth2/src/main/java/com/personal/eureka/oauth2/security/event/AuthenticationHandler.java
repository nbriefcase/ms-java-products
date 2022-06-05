package com.personal.eureka.oauth2.security.event;

import brave.Tracer;
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

    @Autowired
    private Tracer tracer;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            return;
        }
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.info("Login success!. " + user.getUsername());

        User byUsername = userService.findByUsername(authentication.getName());
        if (byUsername.getFailedAttempts() != null && byUsername.getFailedAttempts() > 0) {
            byUsername.setFailedAttempts(0);
            userService.update(byUsername, byUsername.getUserId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        String msg = "Login failed!.";
        log.warn(msg);

        try {
            User byUsername = userService.findByUsername(authentication.getName());
            if (byUsername.getFailedAttempts() == null) {
                byUsername.setFailedAttempts(0);
            }

            StringBuilder errors = new StringBuilder(msg);
            byUsername.setFailedAttempts(byUsername.getFailedAttempts() + 1);
            msg = String.format("User \"%s\" attempts: %d", byUsername.getUsername(), byUsername.getFailedAttempts());
            log.info(msg);
            errors.append(msg);

            if (byUsername.getFailedAttempts() >= 3) {
                byUsername.setIsActive(false);
                msg = String.format("User \"%s\" was inactivated!", authentication.getName());
                log.error(msg);
                errors.append(msg);
            }

            userService.update(byUsername, byUsername.getUserId());

            tracer.currentSpan().tag("error.message", errors.toString());
        } catch (FeignException ex) {
            msg = String.format("User \"%s\" not found!", authentication.getName());
            tracer.currentSpan().tag("error.message", msg + ": " + ex.getMessage());
            log.error(msg);
        }
    }
}
