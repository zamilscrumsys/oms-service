package com.scrumsys.authservice.service;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.scrumsys.authservice.dto.request.*;
import com.scrumsys.authservice.dto.response.AuthResponse;
import com.scrumsys.authservice.dto.response.RefreshTokenResponse;
import com.scrumsys.common.exception.ApplicationException;

public interface AuthService {

    //Existing workable
//    AuthResponse login(String clientId, String clientSecret, LoginRequest request);
//    //AuthResponse loginWithEmail(String clientId,String clientSecret,LoginRequest request);
//    AuthResponse loginWithMobile(String clientId,String clientSecret,LoginRequest request);
//    AuthResponse verifyOtp(String clientId,String clientSecret,OtpVerifyRequest request);
//    AuthResponse refreshToken(String clientId,String clientSecret,RefreshTokenRequest request);
//    void forgotPassword(String clientId,String clientSecret,ForgotPasswordRequest request);
//    void resetPassword(String clientId,String clientSecret,ResetPasswordRequest request);
//    void logout(String token, String clientId);
//    boolean validateToken(String token, String clientId);
//    String getUsernameFromToken(String token);


    //newly addded

    String login(String encodedClient,
                 LoginRequest request);

    RefreshTokenResponse getRefreshToken(
            String encodedClient,
            String accessToken);

    void logout(String username);

    void validateRefreshToken(
            String encodedClient,
            String refreshToken);
//    boolean validateRefreshToken(String encodedClient, String refreshToken);




}
