package com.security.user.service;

import com.security.user.Converter.UserConverter;
import com.security.user.dto.request.LoginRequest;
import com.security.user.dto.request.RegisterRequest;
import com.security.user.entity.User;
import com.security.user.exception.DuplicateUserNameException;
import com.security.user.repo.UserRepo;
import com.security.user.utils.AuthUtils;
import org.eclipse.angus.mail.imap.protocol.IMAPProtocol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private EmailService emailService;

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JWTService jwtService;

    @Mock
    private static UserConverter userConverter;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(userService, "apiKey", "correct-api-key");
    }


    @Test
    public void registerUserUnauthorizedApiKey() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("987sumitverma@gmail.com");
        registerRequest.setName("sumit");
        registerRequest.setPassword("123sumit");
        ResponseEntity<?> response = userService.registerUser(registerRequest, "wrong-api-key");
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Dont you have API key?", response.getBody());
    }

    @Test
    public void registerUserDuplicateUserName() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("987sumitverma@gmail.com");
        registerRequest.setName("sumit");
        registerRequest.setPassword("123sumit");
        when(userRepo.existsByUserNameAndIsActive(registerRequest.getEmail(), true)).thenReturn(true);
        DuplicateUserNameException exception = assertThrows(DuplicateUserNameException.class, () -> {
            userService.registerUser(registerRequest, "correct-api-key");
        });

        assertEquals("Username already exists: 987sumitverma@gmail.com", exception.getMessage());
    }

    @Test
    public void registerUserWithUpdatedDetails() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("123sumit");
        request.setName("Test User");

        String otp = "123456";
        String encodedPassword = "encodedPassword";


        when(userRepo.existsByUserNameAndIsActive(request.getEmail(), true)).thenReturn(false);
        when(userRepo.existsByUserNameAndIsActive(request.getEmail(), false)).thenReturn(true);
        when(userRepo.findByUserName(request.getEmail())).thenReturn(new User());
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);
        try (MockedStatic<AuthUtils> authUtilsMock = mockStatic(AuthUtils.class)) {
            // Mock static method generateOtp
            authUtilsMock.when(AuthUtils::generateOtp).thenReturn(otp);
            ResponseEntity<?> response = userService.registerUser(request, "correct-api-key");
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("OTP has been sent to : " + request.getEmail(), response.getBody());

            verify(emailService, times(1)).sendOtpEmail(request.getEmail(), otp);

            verify(userRepo, times(1)).save(any(User.class));
        }
    }

    @Test
    public void registerUserWithNewDetails() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("sumitverma@gmail.com");
        request.setName("sumit");
        request.setPassword("123sumit");

        when(userRepo.existsByUserNameAndIsActive(request.getEmail(), true)).thenReturn(false);
        when(userRepo.existsByUserNameAndIsActive(request.getEmail(), false)).thenReturn(false);
        try (MockedStatic<AuthUtils> authUtilsMock = mockStatic(AuthUtils.class);
             MockedStatic<UserConverter> converterMock = mockStatic(UserConverter.class)) {
            String otp = "123456";
            authUtilsMock.when(AuthUtils::generateOtp).thenReturn(otp);

            User mockUser = new User();
            converterMock.when(() -> UserConverter.toEntity(request, otp)).thenReturn(mockUser);

            when(userRepo.save(any(User.class))).thenReturn(mockUser);

            userService.registerUser(request,"correct-api-key");

            verify(emailService, times(1)).sendOtpEmail(request.getEmail(), otp);
            verify(userRepo, times(1)).save(mockUser);


            verify(emailService, times(1)).sendOtpEmail(request.getEmail(), "123456");
            verify(userRepo, times(1)).save(any(User.class));
        }

    }




    @Test
    public void loginWithWrongEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("987sumitvgmail.com");
        loginRequest.setPassword("123sumit");
        when(userRepo.existsByUserNameAndIsActive(loginRequest.getEmail(), true)).thenReturn(false);
        Exception exception = assertThrows(Exception.class, () -> {
            userService.login(loginRequest);
        });
        assertEquals("Username or password is incorrect", exception.getMessage());
    }
    @Test
    public void loginWithWrongPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("987sumitverma@gmail.com");
        loginRequest.setPassword("12345");
        when(userRepo.existsByUserNameAndIsActive(loginRequest.getEmail(), true)).thenReturn(true);
           when(authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()))).thenReturn(authentication);
           when(authentication.isAuthenticated()).thenReturn(false);
        Exception exception = assertThrows(Exception.class, () -> {
            userService.login(loginRequest);
        });
        assertEquals("Username or password is incorrect", exception.getMessage());
    }
    @Test
    public void loginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("987sumitverma@gmail.com");
        loginRequest.setPassword("123sumit");
        when(userRepo.existsByUserNameAndIsActive(loginRequest.getEmail(), true)).thenReturn(true);
        when(authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(loginRequest.getEmail())).thenReturn("jwt-token");
    }
}