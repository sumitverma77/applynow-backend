package com.security.user.service;

import com.security.user.dto.request.OtpVerificationRequest;
import com.security.user.entity.User;
import com.security.user.exception.EmailSendingException;
import com.security.user.exception.InvalidEmailException;
import com.security.user.repo.UserRepo;
import com.security.user.utils.AuthUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    UserRepo userRepo;

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
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendingException("Failed to send OTP email to: " + email, e);
        }
    }


    public ResponseEntity<String> verifyOtp(OtpVerificationRequest request) {
        String email = request.getEmail();
        String otp = request.getOtp();
        User user = userRepo.findByUserName(email);
        String storedOtp = user.getOtp();
        if (storedOtp != null && storedOtp.equals(otp)) {
                user.setActive(true);
                userRepo.save(user);
                return ResponseEntity.ok("OTP verified successfully.");
            }
        else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }
    }
    public ResponseEntity<String> resendOtp(String email)
    {
        User user = userRepo.findByUserName(email);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found with the email: " + email);
        }
        String newOtp = AuthUtils.generateOtp();
        user.setOtp(newOtp);
            userRepo.save(user);
        sendOtpEmail(email, newOtp);
        return ResponseEntity.ok("A new OTP has been sent to: " + email);
    }
}
