package com.scrumsys.authservice.service.impl;

import com.scrumsys.authservice.service.SmsService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
//import javax.annotation.PostConstruct;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.fromNumber}")
    private String fromNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    @Async
    public void sendOtpSms(String mobileNumber, String otp) {
        try {
            Message message = Message.creator(
                    new PhoneNumber(mobileNumber),
                    new PhoneNumber(fromNumber),
                    "Your OTP code is: " + otp + ". Valid for 5 minutes."
            ).create();

            log.info("SMS sent successfully to: {}. Message SID: {}", mobileNumber, message.getSid());
        } catch (Exception e) {
            log.error("Failed to send SMS to: {}", mobileNumber, e);
            // In production, consider using a fallback SMS provider
        }
    }
}
