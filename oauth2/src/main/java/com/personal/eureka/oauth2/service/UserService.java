package com.personal.eureka.oauth2.service;

import com.personal.eureka.commons.users.models.entity.User;
import com.personal.eureka.oauth2.clients.UserFeignClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User byUsername = userFeignClient.findByUsername(username);

            List<GrantedAuthority> authorities = byUsername.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

            log.info("Username found! ");
            return new org.springframework.security.core.userdetails.User(
                    byUsername.getUsername(), byUsername.getPassword(), byUsername.getIsActive(),
                    true, true, true, authorities);
        } catch (FeignException e) {
            String msg = "User not found!. " + username;
            log.error(msg);
            throw new UsernameNotFoundException(msg);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userFeignClient.findByUsername(username);
    }

    @Override
    public User update(User user, Long id) {
        return userFeignClient.update(user, id);
    }
}
