package com.scrumsys.authservice.service.impl;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.scrumsys.authservice.dto.request.*;
import com.scrumsys.authservice.dto.response.AuthResponse;
import com.scrumsys.authservice.dto.response.RefreshTokenResponse;
import com.scrumsys.authservice.entity.RefreshTokenEntity;
import com.scrumsys.authservice.repository.RefreshTokenRepository;
import com.scrumsys.authservice.service.*;
import com.scrumsys.common.client.UserServiceClient;
import com.scrumsys.common.dto.UserResponse;
import com.scrumsys.authservice.entity.TokenBlacklist;
import com.scrumsys.authservice.repository.TokenBlacklistRepository;
import com.scrumsys.common.constants.AppConstants;
import com.scrumsys.common.exception.ApplicationException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import com.scrumsys.authservice.dto.response.UserResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;





@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final ClientAppService clientService;
    private final UserServiceClient userClient;
    private final JwtService jwtService;
    private final RefreshTokenRepository repo;

    @Override
    public String login(String encodedClient,
                        LoginRequest req) {

        clientService.validateClient(encodedClient);

        UserResponse user =
                userClient.verifyUsernamePassword(
                        req.getUsername(),
                        req.getPassword());


        if(user==null)
            throw new ApplicationException
                    .UnauthorizedException("Invalid user");

        return jwtService.generateAccessToken(
                user.getEmail(),
                user.getRoles());
    }

    @Override
    public RefreshTokenResponse getRefreshToken(
            String clientCredential,
            String accessToken) {

        // 1. Validate Client
        clientService.validateClient(clientCredential);

        // 2. Validate Access Token
        if (!jwtService.validateToken(accessToken)) {
            throw new ApplicationException
                    .UnauthorizedException("Invalid access token");
        }

        // 3. Extract username FROM ACCESS TOKEN
        String username =
                jwtService.extractUsername(accessToken);

        // 4. Generate REFRESH TOKEN (JWT)

        // dirve expire time  use the expiretime to generate refershtoken and save in the database
        // entity
        String refreshToken =
                jwtService.generateRefreshToken(username);

        // 5. Save in DB
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setToken(refreshToken);
        entity.setUsername(username);
        entity.setExpiryTime(
                LocalDateTime.now().plusMinutes(30));
        entity.setLastUsedTime(LocalDateTime.now());

        repo.save(entity);

        return new RefreshTokenResponse(refreshToken, 1800L);  // no need expire
    }

    @Override
    public void validateRefreshToken(
            String encodedClient,
            String refreshToken) {

        clientService.validateClient(encodedClient);

        RefreshTokenEntity rt =
                repo.findByToken(refreshToken)
                        .orElseThrow(()->
                                new ApplicationException
                                        .UnauthorizedException(
                                        "Invalid refresh token"));

        if(rt.getExpiryTime()
                .isBefore(LocalDateTime.now()))
            throw new ApplicationException
                    .UnauthorizedException("Expired");

        // sliding expiry
        rt.setExpiryTime(
                LocalDateTime.now().plusMinutes(30));
        rt.setLastUsedTime(LocalDateTime.now());
        repo.save(rt);
    }


//    @Override
//    public boolean validateRefreshToken(
//            String encodedClient,
//            String refreshToken) {
//
//        try {
//
//            clientService.validateClient(encodedClient);
//
//            RefreshTokenEntity rt =
//                    repo.findByToken(refreshToken)
//                            .orElseThrow(() ->
//                                    new ApplicationException
//                                            .UnauthorizedException("Invalid refresh token"));
//
//            if (rt.getExpiryTime().isBefore(LocalDateTime.now())) {
//                return false;
//            }
//
//            // sliding window
//            rt.setExpiryTime(LocalDateTime.now().plusMinutes(30));
//            rt.setLastUsedTime(LocalDateTime.now());
//            repo.save(rt);
//
//            return true;
//
//        } catch (Exception e) {
//            return false;
//        }
//    }



    @Override
    public void logout(String username) {
        repo.deleteByUsername(username);
    }

}


//Existing workable

//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class AuthServiceImpl implements AuthService {
//
//    private final JwtUtil jwtUtil;
//    private final OtpService otpService;
//    private final ClientAppService clientAppService;
//    private final UserServiceClient userServiceClient;
//    private final TokenBlacklistRepository tokenBlacklistRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final EmailService emailService;
//
//    @Value("${google.oauth.client-id}")
//    private String googleClientId;
//
//    @Override
//    @Transactional
//    public AuthResponse login(
//            String clientId,
//            String clientSecret,
//            LoginRequest request) {
//
//        log.info("Login attempt for {}", request
//                .getEmail());
//
//        // 1️⃣ Validate client
//        clientAppService.validateClient(clientId, clientSecret);
//
//        // 2️⃣ Validate user credentials
//        UserResponse user =
//                userServiceClient.verifyUserCredentials(
//                        request.getEmail(),
//                        null,
//                        request.getPassword()
//                );
//
//        if (user == null) {
//            throw new ApplicationException.UnauthorizedException(
//                    "Invalid username or password");
//        }
//
//        // 3️⃣ Generate ACCESS TOKEN ONLY
//        String accessToken =
//                jwtUtil.generateAccessToken(
//                        user.getEmail(),
//                        clientId,
//                        user.getRoles()
//                );
//
//        return AuthResponse.builder()
//                .accessToken(accessToken)
//                .tokenType("Bearer")
//                .expiresIn(AppConstants.JWT_ACCESS_TOKEN_EXPIRATION / 1000)
//                .username(user.getUsername())
//                .email(user.getEmail())
//                .userId(String.valueOf(user.getId()))
//                .role(user.getRoles())
//                .build();
//    }



// this is not in the use case
//    @Override
//    @Transactional
//
//    public AuthResponse loginWithEmail(String clientId,String clientSecret,LoginRequest request) {
//
//        log.info("Email login attempt for: {}", request.getEmail());
//
//        // Validate client credentials
//        //for commented header type
//       // if (!clientAppService.validateClient(request.getClientId(), request.getClientSecret())) {
//            if (!clientAppService.validateClient(clientId, clientSecret)) {
//
//            throw new ApplicationException.UnauthorizedException("Invalid client credentials");
//        }
//
//        // Verify user credentials with User Service
//        UserResponse user = userServiceClient.verifyUserCredentials(
//                request.getEmail(), null, request.getPassword());
//
//        if (user == null) {
//            throw new ApplicationException.UnauthorizedException("Invalid email or password");
//        }
//
//
//
//        // Send OTP
//        otpService.sendEmailOtp(request.getEmail(), clientId);//request.getClientId());
//
//        log.info("Email OTP sent for user: {}", request.getEmail());
//
//        return AuthResponse.builder()
//                .message("OTP sent to email")
//                .email(request.getEmail())
//                .userId(String.valueOf(user.getId()))
//                .build();
//    }


// these are temporaru disabled
//    @Override
//    @Transactional
//    public AuthResponse loginWithMobile(String clientId,String clientSecret,LoginRequest request) {
//
//        log.info("Mobile login attempt for: {}", request.getMobileNumber());
//
//        // Validate client credentials
//        //for commented header type
//        //if (!clientAppService.validateClient(request.getClientId(), request.getClientSecret())) {
//        if (!clientAppService.validateClient(clientId, clientSecret)) {
//
//            throw new ApplicationException.UnauthorizedException("Invalid client credentials");
//        }
//
//        // Verify user exists with mobile number
//        UserResponse user = userServiceClient.getUserByMobile(request.getMobileNumber());
//
//        if (user == null) {
//            throw new ApplicationException.NotFoundException("User not found with mobile number");
//        }
//
//        // Send OTP
//        otpService.sendMobileOtp(request.getMobileNumber(), clientId);//request.getClientId());
//
//        log.info("Mobile OTP sent for user: {}", request.getMobileNumber());
//
//        return AuthResponse.builder()
//                .message("OTP sent to mobile")
//                .email(user.getEmail())
//                .userId(String.valueOf(user.getId()))
//                .build();
//    }

//    @Override
//    @Transactional
//    public AuthResponse verifyOtp(String clientId,String clientSecret,OtpVerifyRequest request) {
//        log.info("OTP verification attempt");
//
//        // Validate client exists
//        //working for comment header
//       // clientAppService.getClientByClientId(request.getClientId());
//        clientAppService.validateClient(clientId, clientSecret);
//
//
//        String identifier = request.getEmail() != null ? request.getEmail() : request.getMobileNumber();
//        String otpType = request.getEmail() != null ? "EMAIL" : "MOBILE";
//
//        // Verify OTP
//        otpService.verifyOtp(identifier, request.getOtp(), otpType);
//
//        // Get user details
//        UserResponse user = request.getEmail() != null
//                ? userServiceClient.getUserByEmail(request.getEmail())
//                : userServiceClient.getUserByMobile(request.getMobileNumber());
//
//        if (user == null) {
//            throw new ApplicationException.NotFoundException("User not found");
//        }
//
//
//        // Update last login
//        userServiceClient.updateLastLogin(user.getId(),
//                otpType.equals("EMAIL") ? AppConstants.LOGIN_TYPE_LOCAL : AppConstants.LOGIN_TYPE_MOBILE);
//
//        // Generate tokens using JwtUtil
//        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), clientId,user.getRoles());
//        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), clientSecret,user.getRoles());
//
//        log.info("OTP verified successfully for user: {}", user.getEmail());
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


//@Override
//@Transactional
//public AuthResponse refreshToken(String clientId,String clientSecret,RefreshTokenRequest request) {
//
//    log.info("Refresh token request");
//
//    // Validate client
//    //comment for headers type
//    //clientAppService.getClientByClientId(request.getClientId());
//    clientAppService.validateClient(clientId, clientSecret);
//
//
//    // Validate token signature + expiry
//    if (!jwtUtil.validateToken(request.getRefreshToken())) {
//        throw new ApplicationException.UnauthorizedException("Invalid refresh token");
//    }
//
//    // Ensure it's a REFRESH token
//    String tokenType = jwtUtil.getTokenType(request.getRefreshToken());
//    if (!"REFRESH".equals(tokenType)) {
//        throw new ApplicationException.UnauthorizedException("Invalid token type");
//    }
//
//    // Validate clientId match
//    String clientIdFromToken =
//            jwtUtil.getClientIdFromToken(request.getRefreshToken());
//
//    if (!clientId.equals(clientIdFromToken)) {
//        throw new ApplicationException.UnauthorizedException("Client mismatch");
//    }
//
//    // Check blacklist
//    if (tokenBlacklistRepository.existsByToken(request.getRefreshToken())) {
//        throw new ApplicationException.UnauthorizedException("Token has been revoked");
//    }
//
//    // Extract subject
//    String username =
//            jwtUtil.extractUsername(request.getRefreshToken());
//
//    //  Extract roles from refresh token
//    List<String> roles =
//            jwtUtil.getRolesFromToken(request.getRefreshToken());
//
//    // Generate new tokens
//    String newAccessToken =
//            jwtUtil.generateAccessToken(username, clientId, roles);
//
//    String newRefreshToken =
//            jwtUtil.generateRefreshToken(username, clientSecret, roles);
//
//    // Blacklist old refresh token
//    blacklistToken(
//            request.getRefreshToken(),
//            "REFRESH",
//            username,
//            clientId
//            //request.getClientId()
//    );
//
//    log.info("Token refreshed successfully for user: {}", username);
//
//    return AuthResponse.builder()
//            .accessToken(newAccessToken)
//            .refreshToken(newRefreshToken)
//            .tokenType("Bearer")
//            .expiresIn(AppConstants.JWT_ACCESS_TOKEN_EXPIRATION / 1000)
//            .username(username)
//            .build();
//}
//
//
//    @Override
//    @Transactional
//    public void forgotPassword(String clientId,String clientSecret,ForgotPasswordRequest request) {
//        log.info("Forgot password request for: {}", request.getEmail());
//
//        // Validate client
//        //comment for header purpose
//        //clientAppService.getClientByClientId(request.getClientId());
//        clientAppService.validateClient(clientId, clientSecret);
//
//
//        // Check if user exists
//        UserResponse user = userServiceClient.getUserByEmail(request.getEmail());
//        if (user == null) {
//            throw new ApplicationException.NotFoundException("User not found with email");
//        }
//
//        // Generate reset token
//        String resetToken = UUID.randomUUID().toString();
//        LocalDateTime expiryTime = LocalDateTime.now().plusHours(1);
//
//        // Save reset token to user service
//
//        emailService.sendPasswordResetEmail(user.getEmail(),resetToken);
//        userServiceClient.savePasswordResetToken(user.getId(),resetToken,expiryTime);
//        log.info("Password reset token generated for user: {}", request.getEmail());
//    }

//    @Override
//    @Transactional
//    public void resetPassword(String clientId,String clientSecret,ResetPasswordRequest request) {
//        log.info("Reset password request");
//
//        // Validate client
//        //comment for headers purpose
//        //clientAppService.getClientByClientId(request.getClientId());
//        clientAppService.validateClient(clientId, clientSecret);
//
//
//        // Validate and use reset token
//        boolean isValid = userServiceClient.validateResetToken(
//                request.getToken(), request.getNewPassword());
//
//        if (!isValid) {
//            throw new ApplicationException.BadRequestException("Invalid or expired reset token");
//        }
//
//        log.info("Password reset successfully");
//    }
//
//    @Override
//    @Transactional
//    public void logout(String token, String clientId) {
//        log.info("Logout request");
//
//        if (!jwtUtil.validateToken(token)) {
//            throw new ApplicationException.UnauthorizedException("Invalid token");
//        }
//
//        String tokenType = jwtUtil.getTokenType(token);
//        String username = jwtUtil.extractUsername(token);
//
//        // Blacklist the token
//        blacklistToken(token, tokenType, username, clientId);
//
//        log.info("User logged out successfully: {}", username);
//    }

//    @Override
//    public boolean validateToken(String token, String clientId) {
//        try {
//            if (!jwtUtil.validateToken(token)) {
//                return false;
//            }
//
//            // Check if token is blacklisted
//            if (tokenBlacklistRepository.existsByToken(token)) {
//                return false;
//            }
//
//            // Verify client ID matches
//            String tokenClientId = jwtUtil.getClientIdFromToken(token);
//            return tokenClientId != null && tokenClientId.equals(clientId) &&
//                    "ACCESS".equals(jwtUtil.getTokenType(token));
//
//        } catch (Exception e) {
//            log.error("Token validation error: {}", e.getMessage());
//            return false;
//        }
//    }

//    @Override
//    public String getUsernameFromToken(String token) {
//        return jwtUtil.extractUsername(token);
//    }
//
//
//
//    private void blacklistToken(String token, String tokenType, String username, String clientId) {
//        LocalDateTime expiryDate = jwtUtil.extractExpiration(token).toInstant()
//                .atZone(java.time.ZoneId.systemDefault())
//                .toLocalDateTime();
//
//        TokenBlacklist blacklistedToken = new TokenBlacklist();
//        blacklistedToken.setToken(token);
//        blacklistedToken.setTokenType(tokenType);
//        blacklistedToken.setExpiryDate(expiryDate);
//        blacklistedToken.setUsername(username);
//        blacklistedToken.setClientId(clientId);
//        blacklistedToken.setCreatedBy(username);
//
//        tokenBlacklistRepository.save(blacklistedToken);
//
//        log.debug("Token blacklisted: {}", token);
//    }
//}