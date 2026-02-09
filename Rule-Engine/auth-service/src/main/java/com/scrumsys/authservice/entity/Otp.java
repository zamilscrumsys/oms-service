package com.scrumsys.authservice.entity;

import com.scrumsys.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Otp extends BaseEntity {

    @Column(nullable = false)
    private String identifier; // email or mobile number

    @Column(nullable = false)
    private String otpCode;

    @Column(nullable = false)
    private String otpType; // EMAIL or MOBILE

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    @Column(nullable = false)
    private Boolean isUsed = false;

    @Column
    private Integer attempts = 0;
}