package com.scrumsys.authservice.service.impl;



import com.scrumsys.authservice.service.EmailService;
import com.scrumsys.common.constants.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(AppConstants.EMAIL_SUBJECT_OTP);
            message.setText("Your OTP code is: " + otp + "\n\nThis OTP is valid for 5 minutes.");

            mailSender.send(message);
            log.info("OTP email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send OTP email to: {}", toEmail, e);
            // In production, you might want to use a message queue or retry mechanism
        }
    }

    @Override
    @Async
    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject(AppConstants.EMAIL_SUBJECT_PASSWORD_RESET);
            message.setText("To reset your password, use this token: " + resetToken +
                    "\n\nThis token is valid for 1 hour.");

            mailSender.send(message);
            log.info("Password reset email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", toEmail, e);
        }
    }

    @Override
    @Async
    public void sendAccountCreatedEmail(String toEmail, String tempPassword) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Account Has Been Created");

        message.setText("""
        Your account has been created by the administrator.

        Temporary Password: %s

        Please login and change your password immediately.
        """.formatted(tempPassword));

        mailSender.send(message);
    }

}