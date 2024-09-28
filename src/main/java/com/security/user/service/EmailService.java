package com.security.user.service;

import com.security.user.exception.EmailSendingException;
import com.security.user.exception.InvalidEmailException;
import com.security.user.utils.AuthUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

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
}
