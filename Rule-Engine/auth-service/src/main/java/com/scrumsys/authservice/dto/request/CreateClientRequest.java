package com.scrumsys.authservice.dto.request;

import com.scrumsys.authservice.util.ClientType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientRequest {

    @NotBlank(message = "Client name is required")
    private String clientName;

    @NotBlank(message = "Client type is required")
    @Pattern(regexp = "^(WEB|MOBILE|GOOGLE)$", message = "Client type must be WEB or MOBILE or GOOGLE")
    //private String clientType;
    private ClientType clientType;

    @NotBlank(message = "Redirect URI is required")
    private String redirectUri;

    private String description;
}