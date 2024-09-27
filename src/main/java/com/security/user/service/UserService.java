package com.security.user.service;

import com.security.user.Converter.UserConverter;
import com.security.user.entity.User;
import com.security.user.repo.UserRepo;
import com.security.user.dto.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User registerUser(RegisterRequest request) {
        UserConverter converter = new UserConverter();
        User user = converter.toEntity(request);
        return userRepo.save(user);
    }
}
