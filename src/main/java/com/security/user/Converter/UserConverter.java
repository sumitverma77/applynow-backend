package com.security.user.Converter;

import com.security.user.dto.request.RegisterRequest;
import com.security.user.entity.User;
import com.security.user.utils.AuthUtils;
import org.springframework.stereotype.Component;

public class UserConverter {
    public static User toEntity(RegisterRequest request) {

    User user = new User();
    user.setName(request.getName());
    user.setUserName(request.getEmail());
    user.setPassword(request.getPassword());
    user.setOtp(request.getOtp());
    return user;
    }
}
