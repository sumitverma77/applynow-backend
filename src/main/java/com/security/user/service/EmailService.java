package com.security.user.service;

import com.security.user.dto.request.OtpVerificationRequest;
import com.security.user.exception.EmailSendingException;
import com.security.user.exception.InvalidEmailException;
import com.security.user.utils.AuthUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EmailService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String email, String otp) {

        if (!AuthUtils.isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format: " + email);
        }

        String subject = "Welcome to InternBix!";
        String htmlContent = "<html>"
                + "<body>"
                + "<p>Your OTP code is: <strong> <h2> " + otp + " </h2> </strong></p>"
                + "<p>This OTP will expire in 10 minutes. Please do not share it with anyone.</p>"
                + "<p>Thank you for registering with us!</p>"
                + "<p>Best regards,<br>InternBix</p>"
                + "</body>"
                + "</html>";
        redisTemplate.opsForValue().set(email, otp, 10, TimeUnit.MINUTES);
//        System.out.println(redisTemplate.opsForValue().get(email));
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Set true for HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendingException("Failed to send OTP email to: " + email, e);
        }
    }


    public ResponseEntity<String> verifyOtp(OtpVerificationRequest request) {
        String email = request.getEmail();
        String otp = request.getOtp();
        String storedOtp = redisTemplate.opsForValue().get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }
    }
}
