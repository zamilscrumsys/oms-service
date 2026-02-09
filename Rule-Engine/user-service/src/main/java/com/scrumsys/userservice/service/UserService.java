package com.scrumsys.userservice.service;


import com.scrumsys.userservice.dto.request.CreateUserRequest;
import com.scrumsys.userservice.dto.request.UpdateUserRequest;
import com.scrumsys.common.dto.UserResponse;
import com.scrumsys.common.exception.ApplicationException;
import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);

    UserResponse getUserByEmail(String email);
    UserResponse getUserByMobile(String mobileNumber);
    UserResponse getCurrentUser(String token, String clientId);
    void deactivateUser(Long id);
    UserResponse verifyUserCredentials(String email, String mobileNumber, String password);
    void updateLastLogin(Long userId, String loginType);
    void savePasswordResetToken(Long userId, String token, java.time.LocalDateTime expiryTime);
    boolean validateResetToken(String token, String newPassword);
    UserResponse createGoogleUser(String email, String name, String pictureUrl);

    UserResponse updateCurrentUser(UpdateUserRequest request);
    void deleteCurrentUser();

    UserResponse verifyUsernamePassword(
            String username,
            String password);
}