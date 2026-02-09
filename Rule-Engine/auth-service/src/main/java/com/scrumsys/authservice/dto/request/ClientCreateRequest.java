package com.scrumsys.authservice.dto.request;

import com.scrumsys.common.dto.BasicDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClientCreateRequest extends BasicDTO {
    private String orgCode;
    private String orgName;
    private String clientName;
    private String ipAddress;
    private LocalDate expiryDate;
    private String additionalInfo;
    private BigDecimal subscriptionFee;
}

