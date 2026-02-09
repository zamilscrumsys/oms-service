package com.scrumsys.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResponse {
    private Long id;
    private String name;
    private String description;
    private String apiEndpoint;
    private String httpMethod;
}
