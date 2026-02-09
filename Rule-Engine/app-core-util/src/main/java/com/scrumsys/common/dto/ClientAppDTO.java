package com.scrumsys.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientAppDTO extends BasicDTO {
    private Long id;
    private String orgCode;
    private String orgName;
    private String clientId;
    private String clientSecret; // Only for create/update, not for response
    private String clientName;
    private String ipAddress;
    private String additionalInfo;
    private Double subscriptionFee;
    private Date expiryDate;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
