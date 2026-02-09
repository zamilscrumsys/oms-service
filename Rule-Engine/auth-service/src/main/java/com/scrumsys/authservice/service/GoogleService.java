package com.scrumsys.authservice.service;

import com.scrumsys.authservice.dto.request.GoogleLoginRequest;
import com.scrumsys.authservice.dto.response.AuthResponse;

public interface GoogleService {
   // AuthResponse googleLogin(GoogleLoginRequest request);
    AuthResponse googleLogin(
            String clientId,
            String clientSecret,
            GoogleLoginRequest request);
}
