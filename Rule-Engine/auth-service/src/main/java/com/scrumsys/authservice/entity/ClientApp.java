package com.scrumsys.authservice.entity;




import com.scrumsys.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "client_apps",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "org_code"),
                @UniqueConstraint(columnNames = "client_id")
        }
)
@Getter
@Setter
public class ClientApp extends BaseEntity {

    @Column(name = "org_code", nullable = false)
    private String orgCode;

    @Column(name = "org_name", nullable = false)
    private String orgName;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret; // ENCODED

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Column(name = "subscription_fee")
    private BigDecimal subscriptionFee;

    @Column(name = "active")
    private Boolean active = true;
}

