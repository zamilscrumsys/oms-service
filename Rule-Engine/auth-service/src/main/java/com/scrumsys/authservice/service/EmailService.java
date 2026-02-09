package com.scrumsys.authservice.service;

public interface EmailService {
    void sendOtpEmail(String toEmail, String otp);
    void sendPasswordResetEmail(String toEmail, String resetToken);
    void sendAccountCreatedEmail(String toEmail, String tempPassword);
}
