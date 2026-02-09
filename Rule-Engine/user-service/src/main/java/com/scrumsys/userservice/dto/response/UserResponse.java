package com.scrumsys.userservice.dto.response;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private DepartmentResponse department;
    private Set<RoleResponse> roles;
}

