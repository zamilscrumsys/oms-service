
// Last workable filter
//package com.scrumsys.userservice.config;
//
//import com.scrumsys.common.dto.ApiResponse;
//import com.scrumsys.common.dto.TokenValidationResponse;
//import com.scrumsys.userservice.client.AuthServiceClient;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Slf4j
//@RequiredArgsConstructor
//public class TokenValidationFilter extends OncePerRequestFilter {
//
//    private final AuthServiceClient authServiceClient;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//        String clientId = request.getHeader("X-Client-ID");
//
//        log.debug("Token validation - Auth Header: {}, Client ID: {}",
//                authHeader != null ? "Present" : "Missing",
//                clientId);
//
//        if (authHeader != null && authHeader.startsWith("Bearer ") && clientId != null) {
//
//            String token = authHeader.substring(7);
//
//            try {
//                log.debug("Validating token with Auth Service...");
//
//                ApiResponse<TokenValidationResponse> apiResponse =
//                        authServiceClient.validateToken("Bearer " + token, clientId);
//
//                TokenValidationResponse data = apiResponse.getData();
//
//                if (data != null && data.isValid()) {
//
//                    String username = data.getUsername();
//                    log.debug("Token valid for user: {}", username);
//
//                    //
//                    List<GrantedAuthority> authorities =
//                            data.getRoles() != null
//                                    ? data.getRoles().stream()
//                                    .map(SimpleGrantedAuthority::new)
//                                    .collect(Collectors.toList())
//                                    : Collections.emptyList();
//
//
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(
//                                    username,
//                                    null,
//                                    authorities
//                            );
//
//                    authentication.setDetails(
//                            new WebAuthenticationDetailsSource().buildDetails(request)
//                    );
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                    log.debug("Authentication set with authorities: {}", authorities);
//
//                } else {
//                    log.warn("Invalid token");
//                }
//
//            } catch (Exception ex) {
//                log.error("Token validation failed", ex);
//            }
//        } else {
//            log.debug("Missing Authorization header or Client ID");
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}



// workable mode
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//        String clientId = request.getHeader("X-Client-ID");
//
//        log.debug("Token validation - Auth Header: {}, Client ID: {}",
//                authHeader != null ? "Present" : "Missing",
//                clientId);
//
//        if (authHeader != null && authHeader.startsWith("Bearer ") && clientId != null) {
//
//            String token = authHeader.substring(7);
//
//            try {
//                log.debug("Validating token with Auth Service...");
//
//                ApiResponse<TokenValidationResponse> apiResponse =
//                        authServiceClient.validateToken("Bearer " + token, clientId);
//
//                TokenValidationResponse data = apiResponse.getData();
//
//                if (data != null && data.isValid()) {
//
//                    String username = data.getUsername();
//                    log.debug("Token valid for user: {}", username);
//
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(
//                                    username,          // principal
//                                    null,              // credentials
//                                    Collections.emptyList() // authorities (roles later)
//                            );
//
//                    authentication.setDetails(
//                            new WebAuthenticationDetailsSource().buildDetails(request)
//                    );
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    log.debug("Authentication set in SecurityContext");
//
//                } else {
//                    log.warn("Invalid token");
//                }
//
//            } catch (Exception ex) {
//                log.error("Token validation failed", ex);
//            }
//        } else {
//            log.debug("Missing Authorization header or Client ID");
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
