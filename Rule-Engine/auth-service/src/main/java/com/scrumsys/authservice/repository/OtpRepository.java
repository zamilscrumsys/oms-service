package com.scrumsys.authservice.repository;

import com.scrumsys.authservice.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByIdentifierAndOtpCodeAndOtpTypeAndIsUsedFalseAndExpiryTimeAfter(
            String identifier, String otpCode, String otpType, LocalDateTime now);

    Optional<Otp> findTopByIdentifierAndOtpTypeAndIsUsedFalseOrderByCreatedAtDesc(
            String identifier, String otpType);

    Long countByIdentifierAndOtpTypeAndCreatedAtAfter(
            String identifier, String otpType, LocalDateTime time);
}