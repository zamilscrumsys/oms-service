package com.scrumsys.authservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenResponse {
    private String refreshToken;
    private Long expiresIn;
}

