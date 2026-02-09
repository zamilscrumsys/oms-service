package com.scrumsys.authservice.service;

import com.scrumsys.authservice.dto.response.OtpResponse;

public interface OtpService {
    OtpResponse sendEmailOtp(String email, String clientId);
    OtpResponse sendMobileOtp(String mobileNumber, String clientId);
    boolean verifyOtp(String identifier, String otp, String otpType);
    String generateOtp();


}