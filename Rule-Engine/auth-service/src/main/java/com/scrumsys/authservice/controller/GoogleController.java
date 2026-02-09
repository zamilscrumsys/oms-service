//package com.scrumsys.authservice.controller;
//
//import com.scrumsys.authservice.dto.request.GoogleLoginRequest;
//import com.scrumsys.authservice.dto.response.AuthResponse;
//import com.scrumsys.authservice.service.GoogleService;
//import com.scrumsys.common.controller.BaseController;
//import com.scrumsys.common.dto.ApiResponse;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/google/auth")
//@RequiredArgsConstructor
//public class GoogleController extends BaseController {
//
//    private final GoogleService googleService;
//
//
//
//    @PostMapping("/google-login")
//    public ResponseEntity<ApiResponse<AuthResponse>> googleLogin(
//            @RequestHeader("X-Client-ID") String clientId,
//            @RequestHeader("X-Client-Secret") String clientSecret,
//            @RequestBody GoogleLoginRequest request) {
//
//        AuthResponse response = googleService.googleLogin(clientId,clientSecret,request);
//        return ResponseEntity.ok(ApiResponse.success("Google login successful", response));
//    }
//
//}
