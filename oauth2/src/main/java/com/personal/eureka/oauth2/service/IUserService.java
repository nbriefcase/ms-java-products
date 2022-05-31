package com.personal.eureka.oauth2.service;

import com.personal.eureka.commons.users.models.entity.User;

public interface IUserService {

    User findByUsername(String username);

    User update(User user, Long id);
}
