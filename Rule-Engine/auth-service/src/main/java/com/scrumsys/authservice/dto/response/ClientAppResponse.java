package com.scrumsys.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientAppResponse {
    private Long id;
    private String clientName;
    private String clientId;
    private String clientSecret; // Only shown once during creation
    private String clientType;
    private String redirectUri;
    private String description;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}