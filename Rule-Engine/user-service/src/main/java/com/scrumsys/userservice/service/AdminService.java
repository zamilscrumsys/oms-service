package com.scrumsys.userservice.service;

import com.scrumsys.common.dto.UserResponse;
import com.scrumsys.userservice.dto.request.CreateUserRequest;
import com.scrumsys.userservice.dto.request.UpdateUserRequest;

import java.util.List;

public interface AdminService {

    UserResponse createUser(CreateUserRequest request);
    UserResponse updateUserByAdmin(Long id, UpdateUserRequest request);
    UserResponse getUserById(Long id);
    void deleteUserById(Long id);
    List<UserResponse> getAllUsers();
    void assignRoles(Long userId, List<Long> roleIds);
    void assignDepartment(Long userId, Long departmentId);
}
