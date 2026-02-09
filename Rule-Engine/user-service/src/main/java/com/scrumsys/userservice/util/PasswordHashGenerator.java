package com.scrumsys.userservice.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordHashGenerator {

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void generate() {
        log.warn("BCrypt(Admin@123) = {}",
                passwordEncoder.encode("Admin@123"));
    }
}

