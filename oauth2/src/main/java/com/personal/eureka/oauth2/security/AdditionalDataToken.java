package com.personal.eureka.oauth2.security;

import com.personal.eureka.commons.users.models.entity.User;
import com.personal.eureka.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AdditionalDataToken implements TokenEnhancer {

    @Autowired
    private UserService userService;


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> data = new HashMap<>();
        User byUsername = userService.findByUsername(authentication.getName());
        data.put("email", byUsername.getEmail());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(data);
        
        return accessToken;
    }
}
