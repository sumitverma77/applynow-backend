package com.security.user.service;

import com.security.user.Converter.UserConverter;
import com.security.user.entity.User;
import com.security.user.exception.DuplicateUserNameException;
import com.security.user.repo.UserRepo;
import com.security.user.dto.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    public User registerUser(RegisterRequest request) {

        if (userRepo.existsByUserName(request.getEmail())) {
            throw new DuplicateUserNameException("Username already exists: " + request.getEmail());
        }
       request.setPassword( passwordEncoder.encode(request.getPassword()));
        User user = UserConverter.toEntity(request);
        return userRepo.save(user);
    }
}
