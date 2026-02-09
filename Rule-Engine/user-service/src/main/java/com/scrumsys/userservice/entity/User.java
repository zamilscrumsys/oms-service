package com.scrumsys.userservice.entity;


import com.scrumsys.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "mobile_number")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "login_type")
    private String loginType; // LOCAL, GOOGLE, MOBILE

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Column(name = "mobile_verified")
    private Boolean mobileVerified = false;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Column(name = "password_reset_expiry")
    private LocalDateTime passwordResetExpiry;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;



    @Column(nullable = false)
    private Boolean passwordTemporary = true;

    @Column(nullable = false)
    private Boolean active = true;

}
