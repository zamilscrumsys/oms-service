package com.scrumsys.authservice.repository;

import com.scrumsys.authservice.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshTokenEntity,Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    void deleteByUsername(String username);
}

