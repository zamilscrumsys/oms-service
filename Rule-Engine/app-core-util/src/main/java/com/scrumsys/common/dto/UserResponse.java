package com.scrumsys.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String mobileNumber;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private LocalDateTime lastLogin;
    private String loginType;
    private Boolean emailVerified;
    private Boolean mobileVerified;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean passwordTemporary;


    private Boolean active;

    // added 29-12 recent for Jwt token
    private List<String> roles;
}