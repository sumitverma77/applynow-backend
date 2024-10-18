package com.security.user.service;

import com.security.user.utils.AuthUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void sendOtpWithInvalidEmail() {
        String email = "987sumitvermamail.com";
        String otp = "123456";
        // Mock the static method AuthUtils.isValidEmail
        try (MockedStatic<AuthUtils> authUtilsMockedStatic = mockStatic(AuthUtils.class)) {
            authUtilsMockedStatic.when(() -> AuthUtils.isValidEmail(email)).thenReturn(false);

            Exception exception = assertThrows(Exception.class, () -> {
                emailService.sendOtpEmail(email, otp);
            });
            assertEquals("Invalid email format: " + email, exception.getMessage());
        }
    }
    @Test
    public void sendOtpWithValidEmail()
    {
        String email = "987sumitverma@gmail.com";
        String otp = "123456";
        try (MockedStatic<AuthUtils> authUtilsMockedStatic = mockStatic(AuthUtils.class)) {
            authUtilsMockedStatic.when(() -> AuthUtils.isValidEmail(email)).thenReturn(true);




    }
        }
}
