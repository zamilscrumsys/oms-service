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

