package com.security.user.dto.request;

import lombok.Data;

@Data
public class OtpVerificationRequest {
    String email;
    String otp;
}
