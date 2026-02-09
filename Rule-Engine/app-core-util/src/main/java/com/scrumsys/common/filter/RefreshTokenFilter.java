package com.scrumsys.common.filter;



import com.scrumsys.common.client.AuthFeignClient;
import com.scrumsys.common.dto.ApiResponse;
import com.scrumsys.common.dto.TokenValidationResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final AuthFeignClient authClient;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        return path.startsWith("/api/v1/users/verify")
                || path.startsWith("/api/v1/users/usernamepassword/verify")
                || path.startsWith("/api/v1/users/verify-username-password")
                || path.startsWith("/api/v1/auth/login")
                || path.startsWith("/api/v1/auth/validate-refresh")
                || path.startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String clientCredential =
                request.getHeader("X-CLIENT-CREDENTIAL");

        String refreshToken =
                request.getHeader("X-Refresh-Token");

        if (clientCredential == null || refreshToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter()
                    .write("Missing client credential or refresh token");
            return;
        }

        try {
            // Auth-service will throw 401 if invalid
            authClient.validateRefresh(
                    clientCredential,
                    refreshToken
            );
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter()
                    .write("Invalid or expired refresh token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}



