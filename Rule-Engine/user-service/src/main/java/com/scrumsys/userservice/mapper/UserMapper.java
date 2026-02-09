package com.scrumsys.userservice.mapper;

import com.scrumsys.common.dto.UserResponse;
import com.scrumsys.userservice.entity.Role;
import com.scrumsys.userservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    // Only ONE mapping method
    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profilePicture(user.getProfilePicture())
                .lastLogin(user.getLastLogin())
                .loginType(user.getLoginType())
                .emailVerified(user.getEmailVerified())
                .mobileVerified(user.getMobileVerified())
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roles(user.getRoles().stream().map(Role::getName).toList())
                .build();
    }
}
