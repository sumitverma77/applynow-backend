package com.security.user.service;

import com.security.user.Converter.UserConverter;
import com.security.user.dto.request.LoginRequest;
import com.security.user.entity.User;
import com.security.user.exception.DuplicateUserNameException;
import com.security.user.repo.UserRepo;
import com.security.user.dto.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;
    public User registerUser(RegisterRequest request) {

        if (userRepo.existsByUserName(request.getEmail())) {
            throw new DuplicateUserNameException("Username already exists: " + request.getEmail());
        }
       request.setPassword( passwordEncoder.encode(request.getPassword()));
        User user = UserConverter.toEntity(request);
        return userRepo.save(user);
    }
    public UserDetails login(LoginRequest request) {
        User user = userRepo.findByUserName(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return userDetailsService.loadUserByUsername(user.getUserName());
    }
    /*TODO: agr khud ka response bhejna ho
       jisme kuch extra attributes chahie to ye use kr lena
     */
//    public LoginResponse login2(LoginRequest request) {
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//        if (authentication == null) {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
//        return
//    }
}
