package com.security.user.Converter;

import com.security.user.dto.request.RegisterRequest;
import com.security.user.entity.User;

public class UserConverter {
    public User toEntity(RegisterRequest request) {

    User user = new User();
    user.setName(request.getName());
    user.setUserName(request.getEmail());
    user.setPassword(request.getPassword());
    return user;
    }
}
