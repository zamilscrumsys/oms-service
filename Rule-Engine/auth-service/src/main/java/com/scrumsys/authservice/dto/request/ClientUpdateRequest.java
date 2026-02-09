package com.scrumsys.authservice.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClientUpdateRequest {
    private String orgName;
    private String clientName;
    private String ipAddress;
    private LocalDate expiryDate;
    private String additionalInfo;
    private BigDecimal subscriptionFee;
    private Boolean active;
}

