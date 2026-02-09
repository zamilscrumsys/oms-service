package com.scrumsys.authservice.entity;

import com.scrumsys.common.entity.BasicEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="refresh_tokens")
@Getter @Setter
public class RefreshTokenEntity extends BasicEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true,length=500)
    private String token;

    private String username;

    private LocalDateTime expiryTime;

    private LocalDateTime lastUsedTime;
}


