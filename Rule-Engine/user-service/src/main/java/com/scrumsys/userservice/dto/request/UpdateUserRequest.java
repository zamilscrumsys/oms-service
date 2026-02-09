package com.scrumsys.userservice.dto.request;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String username;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid mobile number format")
    private String mobileNumber;

    private String firstName;
    private String lastName;
    private String profilePicture;
    private String password;

    private Long departmentId;
    private Set<Long> roleIds;
}
