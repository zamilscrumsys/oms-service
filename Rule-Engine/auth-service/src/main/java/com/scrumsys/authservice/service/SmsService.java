package com.scrumsys.authservice.service;



public interface SmsService {
    void sendOtpSms(String mobileNumber, String otp);
}