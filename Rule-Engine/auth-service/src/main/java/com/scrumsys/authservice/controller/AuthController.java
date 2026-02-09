package com.scrumsys.authservice.controller;


import com.scrumsys.authservice.dto.request.*;
import com.scrumsys.authservice.dto.response.AuthResponse;
import com.scrumsys.authservice.dto.response.OtpResponse;
import com.scrumsys.authservice.dto.response.RefreshTokenResponse;
import com.scrumsys.authservice.service.EmailService;
import com.scrumsys.common.dto.UserResponse;
import com.scrumsys.authservice.service.AuthService;
import com.scrumsys.authservice.service.OtpService;
import com.scrumsys.common.controller.BaseController;
import com.scrumsys.common.dto.ApiResponse;
import com.scrumsys.common.dto.TokenValidationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthService service;
    private final EmailService emailService;

    @PostMapping("/login")
    public ApiResponse<String> login(
            @RequestHeader("X-CLIENT-CREDENTIAL")
            String encodedClient,
            @RequestBody LoginRequest req) {

        return ApiResponse.success(
                "Access Token",
                service.login(encodedClient, req));
    }

    @PostMapping("/refresh-token")
    public ApiResponse<RefreshTokenResponse>
    refreshToken(
            @RequestHeader("X-CLIENT-CREDENTIAL")
            String encodedClient,
            @RequestHeader("Authorization")
            String bearer) {

        String token =
                bearer.replace("Bearer ", "");

        return ApiResponse.success(
                "Refresh Token",
                service.getRefreshToken(
                        encodedClient, token));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @RequestHeader("username") String u) {

        service.logout(u);
        return ApiResponse.success(
                "Logged out", null);
    }


    @PostMapping("/validate-refresh")
    public void validateRefreshToken(
            @RequestHeader("X-CLIENT-CREDENTIAL") String encodedClient,
            @RequestHeader("X-Refresh-Token") String refreshToken) {

        service.validateRefreshToken(
                encodedClient,
                refreshToken);
    }


//    @PostMapping("/validate-refresh")
//    public ApiResponse<TokenValidationResponse> validateRefreshToken(
//            @RequestHeader("X-CLIENT-CREDENTIAL") String encodedClient,
//            @RequestHeader("X-Refresh-Token") String refreshToken) {
//
//        boolean valid =
//                service.validateRefreshToken(
//                        encodedClient,
//                        refreshToken);
//
//        return ApiResponse.success(
//                "Token validation result",
//                TokenValidationResponse.builder()
//                        .valid(valid)
//                        .build()
//        );
//    }



//        @GetMapping("/user-info")
//    public ResponseEntity<ApiResponse<String>> getUserInfoFromToken(
//            @RequestHeader("Authorization") String authorizationHeader) {
//        String token = authorizationHeader.replace("Bearer ", "");
//        String username = service.getUsernameFromToken(token);
//        return ResponseEntity.ok(ApiResponse.success("User info retrieved", username));
//    }


//    @PostMapping("/account-created")
//    public void sendAccountCreatedEmail(
//            @RequestParam String email,
//            @RequestParam String tempPassword) {
//
//        emailService.sendAccountCreatedEmail(email, tempPassword);
//    }
    }



//Existing workable
//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
//public class AuthController extends BaseController {
//
//    private final AuthService authService;
//    private final OtpService otpService;
//    private final UserServiceClient userServiceClient;
//    private final EmailService emailService;
//
//
//
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse<AuthResponse>> login(
//            @RequestHeader("X-Client-ID") String clientId,
//            @RequestHeader("X-Client-Secret") String clientSecret,
//            @Valid @RequestBody LoginRequest request) {
//
//        AuthResponse response =
//                authService.login(clientId, clientSecret, request);
//
//        return ResponseEntity.ok(
//                ApiResponse.success("Login successful", response)
//        );
//    }
//
//
//    // this existing workable method commenting for without otp commented on 29-01-26
////@PostMapping("/login/email")
////public ResponseEntity<ApiResponse<AuthResponse>> loginWithEmail(
////        @RequestHeader("X-Client-ID") String clientId,
////        @RequestHeader("X-Client-Secret") String clientSecret,
////        @RequestBody LoginRequest request) {
////
////    AuthResponse response = authService.loginWithEmail(clientId,clientSecret,request);
////
////    return ResponseEntity.ok(
////            ApiResponse.success("OTP sent to email", response)
////    );
////}
//
//
//    @PostMapping("/login/mobile")
//    public ResponseEntity<ApiResponse<AuthResponse>> loginWithMobile(
//            @RequestHeader("X-Client-ID") String clientId,
//            @RequestHeader("X-Client-Secret") String clientSecret,
//            @RequestBody LoginRequest request) {
//
//        AuthResponse response = authService.loginWithMobile(clientId,clientSecret,request);
//
//        return ResponseEntity.ok(
//                ApiResponse.success("OTP sent to mobile", response)
//        );
//    }
//
//
//    @PostMapping("/verify")
//    public ResponseEntity<ApiResponse<AuthResponse>> verifyOtp(
//            @RequestHeader("X-Client-ID") String clientId,
//            @RequestHeader("X-Client-Secret") String clientSecret,
//            @RequestBody OtpVerifyRequest request) {
//        AuthResponse response = authService.verifyOtp(clientId,clientSecret,request);
//        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
//            @RequestHeader("X-Client-ID") String clientId,
//            @RequestHeader("X-Client-Secret") String clientSecret,
//            @RequestBody RefreshTokenRequest request) {
//        AuthResponse response = authService.refreshToken(clientId,clientSecret,request);
//        return ResponseEntity.ok(ApiResponse.success("Token refreshed", response));
//    }
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<ApiResponse<Void>> forgotPassword(
//            @RequestHeader("X-Client-ID") String clientId,
//            @RequestHeader("X-Client-Secret") String clientSecret,
//            @RequestBody ForgotPasswordRequest request) {
//        authService.forgotPassword(clientId,clientSecret,request);
//        return ResponseEntity.ok(ApiResponse.success("Reset instructions sent to email", null));
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<ApiResponse<Void>> resetPassword(
//            @RequestHeader("X-Client-ID") String clientId,
//            @RequestHeader("X-Client-Secret") String clientSecret,
//            @RequestBody ResetPasswordRequest request) {
//        authService.resetPassword(clientId,clientSecret,request);
//        return ResponseEntity.ok(ApiResponse.success("Password reset successful", null));
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<ApiResponse<Void>> logout(
//            @RequestHeader("Authorization") String authorizationHeader,
//            @RequestHeader("X-Client-ID") String clientId) {
//        String token = authorizationHeader.replace("Bearer ", "");
//        authService.logout(token, clientId);
//        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
//    }
//
//
//
//
//
//    @PostMapping("/validate")
//    public ApiResponse<TokenValidationResponse> validateToken(
//            @RequestHeader("Authorization") String authorization,
//            @RequestHeader("X-Client-ID") String clientId
//    ) {
//        String token = authorization.replace("Bearer ", "");
//
//        if (!authService.validateToken(token, clientId)) {
//            return ApiResponse.success(
//                    "Token validation result",
//                    TokenValidationResponse.builder()
//                            .valid(false)
//                            .build()
//            );
//        }
//
//        String username = authService.getUsernameFromToken(token);
//
//        //  roles already List<String>
//        UserResponse user = userServiceClient.getUserByEmail(username);
//        List<String> roles = user.getRoles();
//
//        return ApiResponse.success(
//                "Token validation result",
//                TokenValidationResponse.builder()
//                        .valid(true)
//                        .username(username)
//                        .roles(roles)
//                        .build()
//        );
//    }
//    @GetMapping("/user-info")
//    public ResponseEntity<ApiResponse<String>> getUserInfoFromToken(
//            @RequestHeader("Authorization") String authorizationHeader) {
//        String token = authorizationHeader.replace("Bearer ", "");
//        String username = authService.getUsernameFromToken(token);
//        return ResponseEntity.ok(ApiResponse.success("User info retrieved", username));
//    }
//
//
//    @PostMapping("/account-created")
//    public void sendAccountCreatedEmail(
//            @RequestParam String email,
//            @RequestParam String tempPassword) {
//
//        emailService.sendAccountCreatedEmail(email, tempPassword);
//    }
//}

