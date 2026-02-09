package com.scrumsys.userservice.controller;
import com.scrumsys.userservice.dto.request.CreateUserRequest;
import com.scrumsys.userservice.dto.request.UpdateUserRequest;
import com.scrumsys.common.dto.UserResponse;
import com.scrumsys.userservice.service.UserService;
import com.scrumsys.common.controller.BaseController;
import com.scrumsys.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestHeader("X-Client-ID") String clientId) {
        String token = authorizationHeader.replace("Bearer ", "");
        UserResponse response = userService.getCurrentUser(token, clientId);
        return ResponseEntity.ok(ApiResponse.success("User retrieved", response));
    }

    @PutMapping("/update")
    public ApiResponse<UserResponse> updateMyProfile(
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return ApiResponse.success(
                "Profile updated",
                userService.updateCurrentUser(request)
        );
    }

    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteMyAccount() {
        userService.deleteCurrentUser();
        return ApiResponse.success("Account deleted", null);
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deactivated", null));
    }

    // Internal endpoints for Auth Service
    @GetMapping("/verify")
    public ResponseEntity<UserResponse> verifyUserCredentials(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobileNumber,
            @RequestParam String password) {
        UserResponse response = userService.verifyUserCredentials(email, mobileNumber, password);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        UserResponse response = userService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/mobile/{mobileNumber}")
    public ResponseEntity<UserResponse> getUserByMobile(@PathVariable String mobileNumber) {
        UserResponse response = userService.getUserByMobile(mobileNumber);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/last-login")
    public ResponseEntity<Void> updateLastLogin(
            @PathVariable Long userId,
            @RequestParam String loginType) {
        userService.updateLastLogin(userId, loginType);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/reset-token")
    public ResponseEntity<Void> savePasswordResetToken(
            @PathVariable Long userId,
            @RequestParam String token,
            @RequestParam LocalDateTime expiryTime) {
        userService.savePasswordResetToken(userId, token, expiryTime);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> validateResetToken(
            @RequestParam String token,
            @RequestParam String newPassword) {
        boolean result = userService.validateResetToken(token, newPassword);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/google")
    public ResponseEntity<UserResponse> createGoogleUser(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String pictureUrl) {
        UserResponse response = userService.createGoogleUser(email, name, pictureUrl);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/usernamepassword/verify")
    public UserResponse verifyUsernamePassword(
            @RequestParam String username,
            @RequestParam String password) {


        return userService.verifyUsernamePassword(
                username, password);
    }

}