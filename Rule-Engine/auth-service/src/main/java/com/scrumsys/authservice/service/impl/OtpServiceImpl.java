package com.scrumsys.authservice.service.impl;

import com.scrumsys.authservice.dto.response.OtpResponse;
import com.scrumsys.authservice.entity.Otp;
import com.scrumsys.authservice.repository.OtpRepository;
import com.scrumsys.authservice.service.EmailService;
import com.scrumsys.authservice.service.OtpService;
import com.scrumsys.authservice.service.SmsService;
import com.scrumsys.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private final SmsService smsService;
    private final SecureRandom random = new SecureRandom();

    @Value("${otp.length}")
    private int otpLength;

    @Value("${otp.expiry.minutes}")
    private int otpExpiryMinutes;

    @Override
    @Transactional
    public OtpResponse sendEmailOtp(String email, String clientId) {
        log.info("Sending email OTP to: {}", email);

        validateOtpRateLimit(email, "EMAIL");

        String otpCode = generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(otpExpiryMinutes);

        Otp otp = new Otp();
        otp.setIdentifier(email);
        otp.setOtpCode(otpCode);
        otp.setOtpType("EMAIL");
        otp.setExpiryTime(expiryTime);
        otp.setCreatedBy(clientId);

        otpRepository.save(otp);

        // Send email asynchronously
        emailService.sendOtpEmail(email, otpCode);

        log.info("Email OTP sent to: {}", email);

        return OtpResponse.builder()
                .message("OTP sent successfully to email")
                .identifier(email)
                .otpType("EMAIL")
                .expiryTime(otpExpiryMinutes * 60L)
                .build();
    }

    @Override
   // @Async
    @Transactional
    public OtpResponse sendMobileOtp(String mobileNumber, String clientId) {
        log.info("Sending mobile OTP to: {}", mobileNumber);

        validateOtpRateLimit(mobileNumber, "MOBILE");

        String otpCode = generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(otpExpiryMinutes);

        Otp otp = new Otp();
        otp.setIdentifier(mobileNumber);
        otp.setOtpCode(otpCode);
        otp.setOtpType("MOBILE");
        otp.setExpiryTime(expiryTime);
        otp.setCreatedBy(clientId);

        otpRepository.save(otp);

        // Send SMS asynchronously
        smsService.sendOtpSms(mobileNumber, otpCode);

        log.info("Mobile OTP sent to: {}", mobileNumber);

        return OtpResponse.builder()
                .message("OTP sent successfully to mobile")
                .identifier(mobileNumber)
                .otpType("MOBILE")
                .expiryTime(otpExpiryMinutes * 60L)
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "otp", key = "#identifier + ':' + #otpType")
    public boolean verifyOtp(String identifier, String otp, String otpType) {
        log.info("Verifying OTP for: {}, type: {}", identifier, otpType);

        Otp otpEntity = otpRepository
                .findByIdentifierAndOtpCodeAndOtpTypeAndIsUsedFalseAndExpiryTimeAfter(
                        identifier, otp, otpType, LocalDateTime.now())
                .orElseThrow(() -> new ApplicationException.BadRequestException(
                        "Invalid or expired OTP"
                ));

        if (otpEntity.getAttempts() >= 3) {
            throw new ApplicationException.BadRequestException(
                    "Maximum OTP attempts exceeded"
            );
        }

        otpEntity.setIsUsed(true);
        otpEntity.setUpdatedBy("SYSTEM");
        otpRepository.save(otpEntity);

        log.info("OTP verified successfully for: {}", identifier);
        return true;
    }

    @Override
    public String generateOtp() {
        StringBuilder otp = new StringBuilder(otpLength);
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }



    private void validateOtpRateLimit(String identifier, String otpType) {
        LocalDateTime fifteenMinutesAgo = LocalDateTime.now().minus(15, ChronoUnit.MINUTES);
        Long otpCount = otpRepository.countByIdentifierAndOtpTypeAndCreatedAtAfter(
                identifier, otpType, fifteenMinutesAgo);

        if (otpCount >= 5) {
            throw new ApplicationException.BadRequestException(
                    "Too many OTP requests. Please try again after 15 minutes."
            );
        }
    }
}
