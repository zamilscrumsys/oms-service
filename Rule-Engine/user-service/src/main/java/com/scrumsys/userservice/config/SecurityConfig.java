//
package com.scrumsys.userservice.config;


import com.scrumsys.common.client.AuthFeignClient;
import com.scrumsys.common.filter.RefreshTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RefreshTokenFilter refreshTokenFilter;

    public SecurityConfig(RefreshTokenFilter refreshTokenFilter) {
        this.refreshTokenFilter = refreshTokenFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**",
                                "/api/v1/users/verify",
                                "/api/v1/users/usernamepassword/verify",
                                "/api/v1/users/verify-username-password",


                                "/api/v1/users/email/*",          // Changed from /email/**
                                "/api/v1/users/mobile/*",         // Changed from /mobile/**
                                "/api/v1/users/*/last-login",     // Changed from /**/last-login
                                "/api/v1/users/*/reset-token",    // Changed from /**/reset-token
                                "/api/v1/users/reset-password",
                                "/api/v1/users/google").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(
                        s -> s.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .addFilterBefore(
                        refreshTokenFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}

