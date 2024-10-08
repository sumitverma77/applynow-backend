package com.security.user.constant;

public class WhitelistedEndpoints {
    public static final String[] USER_ENDPOINTS = {
            "/user/register" ,
            "/user/login" ,
            "/user/verify-otp" ,
            "/user/resend-otp"
    };
    public static final String[] JOB_ENDPOINTS = {
            "/job/delete" ,
            "/job/approve" ,
            "/job/get-all-jobs"
    };
    public static final String[] SWAGGER_ENDPOINTS = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };
}
