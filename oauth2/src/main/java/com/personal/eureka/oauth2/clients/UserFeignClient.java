package com.personal.eureka.oauth2.clients;

import com.personal.eureka.commons.users.models.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("users/search/username")
    User findByUsername(@RequestParam("name") String username);
}
