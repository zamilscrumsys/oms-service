package com.scrumsys.policy.dto.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthValidationResponse {
    private boolean valid;
    private String userId;
    private String role;
}

