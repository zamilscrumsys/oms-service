//package com.scrumsys.policy.config;
//
//
//import com.scrumsys.common.dto.ApiResponse;
//import com.scrumsys.common.dto.TokenValidationResponse;
//
//import com.scrumsys.policy.client.AuthServiceClient;
//import com.scrumsys.policy.dto.responsedto.AuthValidationResponse;
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
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class TokenValidationFilter extends OncePerRequestFilter {
//
//    private final AuthServiceClient authServiceClient;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//        String clientId = request.getHeader("X-Client-ID");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")
//                && clientId != null
//                && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            try {
//                ApiResponse<TokenValidationResponse> apiResponse =
//                        authServiceClient.validateToken(authHeader, clientId);
//
//                TokenValidationResponse data = apiResponse.getData();
//
//                if (data != null && data.isValid()) {
//
//                    List<GrantedAuthority> authorities =
//                            data.getRoles() != null
//                                    ? data.getRoles().stream()
//                                    .map(SimpleGrantedAuthority::new)
//                                    .collect(Collectors.toList())
//                                    : Collections.emptyList();
//
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(
//                                    data.getUsername(),
//                                    null,
//                                    authorities
//                            );
//
//                    authentication.setDetails(
//                            new WebAuthenticationDetailsSource()
//                                    .buildDetails(request)
//                    );
//
//                    SecurityContextHolder.getContext()
//                            .setAuthentication(authentication);
//                }
//            } catch (Exception ex) {
//                log.error("Token validation failed", ex);
//            }
//        }
//
//        //  ALWAYS continue filter chain
//        filterChain.doFilter(request, response);
//    }
//}
//
//
