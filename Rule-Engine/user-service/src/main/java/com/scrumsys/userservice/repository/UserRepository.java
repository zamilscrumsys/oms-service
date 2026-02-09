package com.scrumsys.userservice.repository;
import com.scrumsys.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByMobileNumber(String mobileNumber);

    Optional<User> findByEmailOrMobileNumber(String email, String mobileNumber);

    Boolean existsByEmail(String email);

    Boolean existsByMobileNumber(String mobileNumber);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.mobileNumber = :identifier")
    Optional<User> findByIdentifier(@Param("identifier") String identifier);

//    @Query("SELECT u FROM User u WHERE (u.email = :email OR :email IS NULL) " +
//            "AND (u.mobileNumber = :mobileNumber OR :mobileNumber IS NULL)")

    @Query("""
    SELECT u FROM User u
    WHERE u.isDeleted = false
      AND u.isActive = true
      AND (
            (:email IS NOT NULL AND u.email = :email)
         OR (:mobileNumber IS NOT NULL AND u.mobileNumber = :mobileNumber)
      )
""")
    Optional<User> findByEmailOrMobile(
            @Param("email") String email,
            @Param("mobileNumber") String mobileNumber);

    Optional<User> findByPasswordResetToken(String passwordResetToken);


    @Query("""
    SELECT u FROM User u
    WHERE u.isDeleted = false
      AND (
            (u.email = :email AND :email IS NOT NULL)
         OR (u.mobileNumber = :mobileNumber AND :mobileNumber IS NOT NULL)
      )
""")
    Optional<User> findActiveByEmailOrMobile(
            @Param("email") String email,
            @Param("mobileNumber") String mobileNumber
    );

    Optional<User> findByUsername(String username);

}
