package com.security.user.service;

import com.security.user.Converter.UserConverter;
import com.security.user.dto.request.LoginRequest;
import com.security.user.dto.request.RegisterRequest;
import com.security.user.entity.User;
import com.security.user.exception.DuplicateUserNameException;
import com.security.user.repo.UserRepo;
import com.security.user.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    EmailService emailService;
    @Value("${api.key}")
    private String apiKey;
    public ResponseEntity<?> registerUser(RegisterRequest request ,String apikeyHeader) {
        if(!apiKey.equals(apikeyHeader))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Dont know API key?");
        }
        if (userRepo.existsByUserNameAndIsActive(request.getEmail() , true)) {
            throw new DuplicateUserNameException("Username already exists: " + request.getEmail());
        }

        String otp = AuthUtils.generateOtp();
        request.setOtp(otp);
        emailService.sendOtpEmail(request.getEmail(), otp);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        if(userRepo.existsByUserNameAndIsActive(request.getEmail() , false)) {
            User user =  userRepo.findByUserName(request.getEmail());
            user.setOtp(otp);
            userRepo.save(user);
        }
        else {
            request.setOtp(otp);
            userRepo.save(UserConverter.toEntity(request));
        }
        return ResponseEntity.ok("OTP has been sent to : " + request.getEmail());
    }


    public String login(LoginRequest request)  {
        if(!userRepo.existsByUserNameAndIsActive(request.getEmail() , true)) {
            throw new UsernameNotFoundException("Username or password is incorrect");
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//        if (authentication == null) {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
        if(!authentication.isAuthenticated())
        {
             throw new UsernameNotFoundException("Username or password is incorrect");
        }

        return jwtService.generateToken(request.getEmail());

//        User user = userRepo.findByUserName(request.getEmail());
//        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }
//        return userDetailsService.loadUserByUsername(user.getUserName());
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
