package com.security.user.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    String name;
    String email;
    String password;
}
