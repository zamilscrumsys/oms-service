package com.scrumsys.userservice.service.impl;

import com.scrumsys.common.client.AuthFeignClient;
import com.scrumsys.common.constants.AppConstants;
import com.scrumsys.common.dto.UserResponse;
import com.scrumsys.common.exception.ApplicationException;

import com.scrumsys.userservice.dto.request.CreateUserRequest;
import com.scrumsys.userservice.dto.request.UpdateUserRequest;
import com.scrumsys.userservice.entity.Department;
import com.scrumsys.userservice.entity.Role;
import com.scrumsys.userservice.entity.User;
import com.scrumsys.userservice.mapper.UserMapper;
import com.scrumsys.userservice.repository.DepartmentRepository;
import com.scrumsys.userservice.repository.RoleRepository;
import com.scrumsys.userservice.repository.UserRepository;
import com.scrumsys.userservice.service.AdminService;
import com.scrumsys.userservice.util.Helper;
import com.scrumsys.userservice.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final Helper helper;
    private final AuthFeignClient authServiceClient;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        String tempPassword = PasswordGenerator.generate();
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
        user.setPassword(passwordEncoder.encode(tempPassword));
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

        //sending email to user with temp password

        //this method is disabled for reason filter

        //authServiceClient.sendAccountCreatedEmail(user.getEmail(), tempPassword);

        return userMapper.mapToResponse(savedUser);
    }


    @Override
    @Transactional
    public void deleteUserById(Long userId) {

        User currentAdmin = getAuthenticatedUser();

        if (currentAdmin.getId().equals(userId)) {
            throw new ApplicationException.BadRequestException("Admin cannot delete himself");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException.NotFoundException("User not found"));



        userRepository.delete(user); // cascade handles roles
    }


    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found with id: " + id
                ));

        return userMapper.mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(user -> Boolean.TRUE.equals(user.getIsActive()))
                .map(userMapper::mapToResponse)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public void assignRoles(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found with id: " + userId
                ));

        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ApplicationException.NotFoundException(
                            "Role not found with id: " + roleId
                    ));
            roles.add(role);
        }

        user.setRoles(roles);
        user.setUpdatedBy("SYSTEM");
        userRepository.save(user);

        log.info("Roles assigned to user ID: {}", userId);
    }

    @Override
    @Transactional
    public void assignDepartment(Long userId, Long departmentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found with id: " + userId
                ));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "Department not found with id: " + departmentId
                ));

        user.setDepartment(department);
        user.setUpdatedBy("SYSTEM");
        userRepository.save(user);

        log.info("Department assigned to user ID: {}", userId);
    }

    @Override
    @Transactional
    public UserResponse updateUserByAdmin(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException.NotFoundException(
                        "User not found with id: " + id
                ));

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

        log.info("User updated successfully with ID: {}", id);

        return userMapper.mapToResponse(updatedUser);
    }


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
}
