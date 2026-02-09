package com.scrumsys.authservice.entity;

import com.scrumsys.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_blacklist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenBlacklist extends BaseEntity {

    @Column(name = "token", columnDefinition = "TEXT", nullable = false)
    private String token;

    @Column(nullable = false)
    private String tokenType; // ACCESS or REFRESH

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private String username;

    @Column
    private String clientId;
}