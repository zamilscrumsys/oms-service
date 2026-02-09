package com.scrumsys.userservice.service.impl;

import com.scrumsys.common.client.AuthFeignClient;

import com.scrumsys.userservice.dto.request.CreateUserRequest;
import com.scrumsys.userservice.dto.request.UpdateUserRequest;
import com.scrumsys.common.dto.UserResponse;
import com.scrumsys.userservice.entity.*;
import com.scrumsys.userservice.mapper.UserMapper;
import com.scrumsys.userservice.repository.*;
import com.scrumsys.userservice.service.UserService;
import com.scrumsys.common.constants.AppConstants;
import com.scrumsys.common.exception.ApplicationException;
import com.scrumsys.userservice.util.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final FunctionRepository functionRepository;
    private final AuthFeignClient authServiceClient;
    private final PasswordEncoder passwordEncoder;
    private final Helper helper;
    private final UserMapper userMapper;


// Authenticate token from Auth via TokenValidationFilter
    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new ApplicationException.UnauthorizedException("Unauthenticated");
        }

        String identifier = auth.getName();

        return userRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found: " + identifier
                ));
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating new user: {}", request.getEmail());

        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApplicationException.ConflictException(
                    "User with email " + request.getEmail() + " already exists"
            );
        }

        if (request.getMobileNumber() != null &&
                userRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new ApplicationException.ConflictException(
                    "User with mobile number " + request.getMobileNumber() + " already exists"
            );
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApplicationException.ConflictException(
                    "Username " + request.getUsername() + " already exists"
            );
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCreatedBy("SYSTEM");

        // Assign default role (USER)
        Role userRole = roleRepository.findByName(AppConstants.ROLE_USER)
                .orElseGet(() -> helper.createDefaultRole());
        user.getRoles().add(userRole);

        // Assign default department if specified
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ApplicationException.NotFoundException(
                            "Department not found with id: " + request.getDepartmentId()
                    ));
            user.setDepartment(department);
        }

        User savedUser = userRepository.save(user);

        log.info("User created successfully with ID: {}", savedUser.getId());

        return userMapper.mapToResponse(savedUser);
    }


    @Override
    @Transactional
    public void deleteCurrentUser() {
        User user = getAuthenticatedUser();
        user.setIsActive(false);
        user.setIsDeleted(true);
        userRepository.save(user);
    }


    @Override
    @Transactional
    public UserResponse updateCurrentUser(UpdateUserRequest request) {

        User user = getAuthenticatedUser();


        // Update fields
        if (request.getUsername() != null) {
            if (!request.getUsername().equals(user.getUsername()) &&
                    userRepository.existsByUsername(request.getUsername())) {
                throw new ApplicationException.ConflictException(
                        "Username " + request.getUsername() + " already exists"
                );
            }
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new ApplicationException.ConflictException(
                        "Email " + request.getEmail() + " already exists"
                );
            }
            user.setEmail(request.getEmail());
        }

        if (request.getMobileNumber() != null &&
                !request.getMobileNumber().equals(user.getMobileNumber())) {
            if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
                throw new ApplicationException.ConflictException(
                        "Mobile number " + request.getMobileNumber() + " already exists"
                );
            }
            user.setMobileNumber(request.getMobileNumber());
        }

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if(request.getPassword() !=null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getProfilePicture() != null) {
            user.setProfilePicture(request.getProfilePicture());
        }

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ApplicationException.NotFoundException(
                            "Department not found with id: " + request.getDepartmentId()
                    ));
            user.setDepartment(department);
        }

        if (request.getRoleIds() != null) {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : request.getRoleIds()) {
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new ApplicationException.NotFoundException(
                                "Role not found with id: " + roleId
                        ));
                roles.add(role);
            }
            user.setRoles(roles);
        }

        user.setUpdatedBy("Admin");
        User updatedUser = userRepository.save(user);





// already there
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setProfilePicture(request.getProfilePicture());
        user.setUpdatedBy(user.getUsername());

        return userMapper.mapToResponse(userRepository.save(user));
    }


    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found with email: " + email
                ));

        return userMapper.mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByMobile(String mobileNumber) {
        User user = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found with mobile number: " + mobileNumber
                ));

        return userMapper.mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String token, String clientId) {

        // Token already validated by filter
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new ApplicationException.UnauthorizedException("Unauthenticated");
        }


        String username = auth.getName();

        User user = userRepository.findByIdentifier(username)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found: " + username
                ));

        return userMapper.mapToResponse(user);
    }

    @Override
    @Transactional
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found with id: " + id
                ));

        user.setIsActive(false);
        user.setUpdatedBy("SYSTEM");
        userRepository.save(user);

        log.info("User deactivated successfully with ID: {}", id);
    }
    @Override
    @Transactional(readOnly = true)
    public UserResponse verifyUserCredentials(String email, String mobileNumber, String password) {

        User user = userRepository.findByEmailOrMobile(email, mobileNumber)

                .orElseThrow(() -> new ApplicationException.UnauthorizedException(
                        "Invalid credentials"
                ));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException.UnauthorizedException("Invalid credentials");
        }


        if(!Boolean.TRUE.equals(user.getIsActive())){
            throw new ApplicationException.UnauthorizedException("User account is deactivated");
        }

        return userMapper.mapToResponse(user);
    }

    @Override
    @Transactional
    public void updateLastLogin(Long userId, String loginType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found with id: " + userId
                ));

        user.setLastLogin(LocalDateTime.now());
        user.setLoginType(loginType);
        userRepository.save(user);

        log.debug("Last login updated for user ID: {}", userId);
    }

    @Override
    @Transactional
    public void savePasswordResetToken(Long userId, String token, LocalDateTime expiryTime) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found with id: " + userId
                ));

        user.setPasswordResetToken(token);
        user.setPasswordResetExpiry(expiryTime);
        userRepository.save(user);

        log.debug("Password reset token saved for user ID: {}", userId);
    }

    @Override
    @Transactional
    public boolean validateResetToken(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token)
                .orElseThrow(() -> new ApplicationException.BadRequestException(
                        "Invalid or expired reset token"
                ));


        if (user.getPasswordResetExpiry() == null ||
                user.getPasswordResetExpiry().isBefore(LocalDateTime.now())) {
            throw new ApplicationException.BadRequestException("Reset token has expired");
        }

        if (!token.equals(user.getPasswordResetToken())) {
            throw new ApplicationException.BadRequestException("Invalid reset token");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetExpiry(null);
        user.setUpdatedBy("SYSTEM");
        userRepository.save(user);

        log.info("Password reset successful for user ID: {}", user.getId());
        return true;
    }

    @Override
    @Transactional
    public UserResponse createGoogleUser(String email, String name, String pictureUrl) {

        log.info("Creating Google user: {}", email);

        if (userRepository.existsByEmail(email)) {
            throw new ApplicationException.ConflictException(
                    "User with email " + email + " already exists"
            );
        }

        // Extract username from email
        String username = email.split("@")[0];
        int counter = 1;
        String originalUsername = username;

        while (userRepository.existsByUsername(username)) {
            username = originalUsername + counter;
            counter++;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); // Random password
        user.setFirstName(name);
        user.setProfilePicture(pictureUrl);
        user.setEmailVerified(true);
        user.setLoginType(AppConstants.LOGIN_TYPE_GOOGLE);
        user.setCreatedBy("GOOGLE_OAUTH");

        // Assign default role
        Role userRole = roleRepository.findByName(AppConstants.ROLE_USER)
                .orElseGet(() -> helper.createDefaultRole());
        user.getRoles().add(userRole);

        User savedUser = userRepository.save(user);

        log.info("Google user created successfully with ID: {}", savedUser.getId());

        return userMapper.mapToResponse(savedUser);
    }


    @Override
    @Transactional(readOnly = true)
    public UserResponse verifyUsernamePassword(
            String username,
            String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ApplicationException.UnauthorizedException(
                                "Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApplicationException.UnauthorizedException(
                    "Invalid username or password");
        }

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new ApplicationException.UnauthorizedException(
                    "User account is deactivated");
        }

        return userMapper.mapToResponse(user);
    }






}