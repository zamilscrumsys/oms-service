package com.scrumsys.authservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientResponse {

    private String orgCode;
    private String clientId;

    // RETURN ONLY ONCE WHEN CREATED
    private String clientSecret;

    // encoded(clientId:clientSecret)
    //private String encodedCredential;

    private String clientName;
    private String orgName;
    private LocalDate expiryDate;
    private Boolean active;
}

