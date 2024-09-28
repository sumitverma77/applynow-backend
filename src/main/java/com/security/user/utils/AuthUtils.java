package com.security.user.utils;

import java.util.regex.Pattern;

public class AuthUtils {
    public static String generateOtp() {
        return String.valueOf((int) ((Math.random() * 900000) + 100000));
    }
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
