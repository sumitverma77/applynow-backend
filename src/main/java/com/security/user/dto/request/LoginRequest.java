package com.security.user.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
