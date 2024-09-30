package com.security.user.controller;

import com.security.user.dto.request.LoginRequest;
import com.security.user.dto.request.OtpVerificationRequest;
import com.security.user.entity.User;
import com.security.user.dto.request.RegisterRequest;
import com.security.user.service.EmailService;
import com.security.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    EmailService emailService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest);
    }
    @PostMapping("login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest); // Call the login method
    }
    @PostMapping("verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequest request) {
        return emailService.verifyOtp(request);
    }
    @PostMapping("resend-otp")
        public ResponseEntity<String> resend(@RequestHeader("email") String email)
        {
                   return  emailService.resendOtp(email);
        }
     @GetMapping ("test")
     public void test()
     {
         System.out.println("test");
     }
    @GetMapping("csrf/")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        System.out.println("hi");
        return (CsrfToken) request.getAttribute("_csrf");
    }

}
