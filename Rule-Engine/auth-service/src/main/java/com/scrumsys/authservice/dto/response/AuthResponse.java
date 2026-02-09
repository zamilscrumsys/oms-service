package com.scrumsys.authservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String message;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;
    private String username;
    private String email;
    private String userId;
    private List<String> role;
}
