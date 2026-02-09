//package com.scrumsys.common.config.security;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
////@Configuration
//public class SecurityConfig {
//
//    //@Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//
//}
package com.scrumsys.common.config.security;

import com.scrumsys.common.filter.RefreshTokenFilter;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    private final RefreshTokenFilter refreshTokenFilter;
//
//    public SecurityConfig(
//            RefreshTokenFilter refreshTokenFilter) {
//        this.refreshTokenFilter = refreshTokenFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)
//            throws Exception {
//
//        http
//                // Disable CSRF
//                .csrf(AbstractHttpConfigurer::disable)
//
//                // Disable default login mechanisms
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//
//                // Allow everything
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/api/v1/auth/**",
//                                "/api/v1/clients/**",
//                                "/actuator/**",
//                                "/api/v1/users/verify",
//                                "api/v1/users/usernamepassword/verify",
//                                "/api/v1/users/email/*",          // Changed from /email/**
//                                "/api/v1/users/mobile/*",         // Changed from /mobile/**
//                                "/api/v1/users/*/last-login",     // Changed from /**/last-login
//                                "/api/v1/users/*/reset-token",    // Changed from /**/reset-token
//                                "/api/v1/users/reset-password",
//                                "/api/v1/users/google",
//                                "/api/v1/auth/account-created",
//                                "/api/v1/auth/validate-refresh"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                        //permitAll()
//                ).addFilterBefore(refreshTokenFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//}