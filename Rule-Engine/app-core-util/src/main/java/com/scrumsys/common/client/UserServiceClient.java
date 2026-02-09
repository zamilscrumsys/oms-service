package com.scrumsys.common.client;

import com.scrumsys.common.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@FeignClient(name = "user-service", url = "http://localhost:8088")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/verify")
    UserResponse verifyUserCredentials(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "mobileNumber", required = false) String mobileNumber,
            @RequestParam("password") String password);

    @GetMapping("/api/v1/users/email/{email}")
    UserResponse getUserByEmail(@PathVariable("email") String email);

    @GetMapping("/api/v1/users/mobile/{mobileNumber}")
    UserResponse getUserByMobile(@PathVariable("mobileNumber") String mobileNumber);

    @PutMapping("/api/v1/users/{userId}/last-login")
    void updateLastLogin(@PathVariable("userId") Long userId, @RequestParam("loginType") String loginType);

    @PostMapping("/api/v1/users/{userId}/reset-token")
    void savePasswordResetToken(
            @PathVariable("userId") Long userId,
            @RequestParam("token") String token,
            @RequestParam("expiryTime") LocalDateTime expiryTime);

    @PostMapping("/api/v1/users/reset-password")
    boolean validateResetToken(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword);

    @PostMapping("/api/v1/users/google")
    UserResponse createGoogleUser(
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("pictureUrl") String pictureUrl);


    @GetMapping("/api/v1/users/usernamepassword/verify")
    UserResponse verifyUsernamePassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password);

}

