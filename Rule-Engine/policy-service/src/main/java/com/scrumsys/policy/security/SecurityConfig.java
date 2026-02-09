package com.scrumsys.policy.security;

import com.scrumsys.common.filter.RefreshTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

        http
                .csrf(csrf -> csrf.disable())

                // IMPORTANT
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().permitAll()
                )

                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(
                        refreshTokenFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}






//recent commented
//import com.scrumsys.policy.config.TokenValidationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final TokenValidationFilter tokenValidationFilter;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests(auth -> auth
//                        // allow actuator if needed
//                        .requestMatchers("/actuator/**").permitAll()
//
//                        // 🔐 ALL variable APIs REQUIRE AUTH
//                        .requestMatchers("/api/**").authenticated()
//
//                        .anyRequest().denyAll()
//                )
//                .addFilterBefore(
//                        tokenValidationFilter,
//                        UsernamePasswordAuthenticationFilter.class
//                );
//
//        return http.build();
//    }
//}
//
//
//
//
