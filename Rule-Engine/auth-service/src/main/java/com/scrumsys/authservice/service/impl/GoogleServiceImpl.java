package com.scrumsys.authservice.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
//import com.scrumsys.authservice.client.UserServiceClient;
import com.scrumsys.authservice.dto.request.GoogleLoginRequest;
import com.scrumsys.authservice.dto.response.AuthResponse;
import com.scrumsys.authservice.entity.ClientApp;
import com.scrumsys.authservice.repository.TokenBlacklistRepository;
import com.scrumsys.authservice.service.ClientAppService;
import com.scrumsys.authservice.service.GoogleService;
import com.scrumsys.authservice.util.ClientType;
//import com.scrumsys.authservice.util.JwtUtil;
import com.scrumsys.common.constants.AppConstants;
import com.scrumsys.common.dto.UserResponse;
import com.scrumsys.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


 //recent workable for comment oauth2.0 on 05-02-2026
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class GoogleServiceImpl implements GoogleService {
//
//
//    private final UserServiceClient userServiceClient;
//    private final JwtUtil jwtUtil;
//    private final ClientAppService clientAppService;
//
//    @Value("${google.oauth.client-id}")
//    private String googleOAuthClientId;
//
//
//
//        @Override
//        @Transactional
//        public AuthResponse googleLogin(
//                String clientId,
//                String clientSecret,
//                GoogleLoginRequest request) {
//
//            log.info("Google login attempt");
//
//            // 1️⃣ Validate INTERNAL client credentials
//            ClientApp client =
//                    clientAppService.validateAndGetClient(clientId, clientSecret);
//
//            // 2️⃣ Ensure this client is allowed to use GOOGLE login
//            if (!ClientType.GOOGLE.equals(client.getClientType())) {
//                throw new ApplicationException.BadRequestException(
//                        "Client is not configured for GOOGLE login");
//            }
//
//            // 3️⃣ Verify Google ID token
//            GoogleIdToken idToken = verifyGoogleToken(request.getIdToken());
//            if (idToken == null) {
//                throw new ApplicationException.UnauthorizedException(
//                        "Invalid Google ID token");
//            }
//
//            GoogleIdToken.Payload payload = idToken.getPayload();
//
//            String email = payload.getEmail();
//            String name = (String) payload.get("name");
//            String pictureUrl = (String) payload.get("picture");
//
//            // 4️⃣ Find or create user
//            UserResponse user;
//            try {
//                user = userServiceClient.getUserByEmail(email);
//            } catch (Exception ex) {
//                log.info("User not found, creating Google user: {}", email);
//                user = userServiceClient.createGoogleUser(email, name, pictureUrl);
//            }
//
//            // 5️⃣ Update last login
//            userServiceClient.updateLastLogin(
//                    user.getId(),
//                    AppConstants.LOGIN_TYPE_GOOGLE
//            );
//
//            // 6️⃣ Extract roles
//            List<String> roles =
//                    (user.getRoles() != null && !user.getRoles().isEmpty())
//                            ? user.getRoles()
//                            : List.of(AppConstants.ROLE_USER);
//
//            // 7️⃣ Generate JWTs
//            String accessToken =
//                    jwtUtil.generateAccessToken(email, clientId, roles);
//
//            String refreshToken =
//                    jwtUtil.generateRefreshToken(email, clientId, roles);
//
//            return AuthResponse.builder()
//                    .accessToken(accessToken)
//                    .refreshToken(refreshToken)
//                    .tokenType("Bearer")
//                    .expiresIn(AppConstants.JWT_ACCESS_TOKEN_EXPIRATION / 1000)
//                    .username(user.getUsername())
//                    .email(user.getEmail())
//                    .userId(String.valueOf(user.getId()))
//                    .build();
//        }
//
//        // 🔐 Google ID token verification
//        private GoogleIdToken verifyGoogleToken(String idTokenString) {
//            try {
//                GoogleIdTokenVerifier verifier =
//                        new GoogleIdTokenVerifier.Builder(
//                                new NetHttpTransport(),
//                                JacksonFactory.getDefaultInstance()
//                        )
//                                .setAudience(Collections.singletonList(googleOAuthClientId))
//                                .build();
//
//                return verifier.verify(idTokenString);
//
//            } catch (Exception e) {
//                log.error("Google token verification failed", e);
//                return null;
//            }
//        }

















    //workingmode comment for use the credentials in headers
//    @Override
//    @Transactional
//    public AuthResponse googleLogin(GoogleLoginRequest request) {
//
//        log.info("Google login attempt");
//
//        GoogleIdToken idToken = verifyGoogleToken(request.getIdToken());
//        if (idToken == null) {
//            throw new ApplicationException.UnauthorizedException("Invalid Google token");
//        }
//
//        GoogleIdToken.Payload payload = idToken.getPayload();
//
//        String email = payload.getEmail();
//        String name = (String) payload.get("name");
//        String pictureUrl = (String) payload.get("picture");
//
//        UserResponse user;
//
//        try {
//            user = userServiceClient.getUserByEmail(email);
//        } catch (Exception ex) {
//            log.info("User not found, creating Google user: {}", email);
//            user = userServiceClient.createGoogleUser(email, name, pictureUrl);
//        }
//
//        userServiceClient.updateLastLogin(
//                user.getId(),
//                AppConstants.LOGIN_TYPE_GOOGLE
//        );
//
//        //  Extract roles (fallback to ROLE_USER)
//        List<String> roles =
//                user.getRoles() != null && !user.getRoles().isEmpty()
//                        ? user.getRoles()
//                        : List.of(AppConstants.ROLE_USER);
//
//        //  Generate tokens WITH roles
//        String accessToken =
//                jwtUtil.generateAccessToken(email, "GOOGLE", roles);
//
//        String refreshToken =
//                jwtUtil.generateRefreshToken(email, "GOOGLE", roles);
//
//        return AuthResponse.builder()
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .tokenType("Bearer")
//                .expiresIn(AppConstants.JWT_ACCESS_TOKEN_EXPIRATION / 1000)
//                .username(user.getUsername())
//                .email(user.getEmail())
//                .userId(String.valueOf(user.getId()))
//                .build();
//    }




