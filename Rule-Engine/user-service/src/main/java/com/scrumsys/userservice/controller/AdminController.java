package com.scrumsys.userservice.controller;

import com.scrumsys.common.controller.BaseController;
import com.scrumsys.common.dto.ApiResponse;
import com.scrumsys.common.dto.UserResponse;
import com.scrumsys.userservice.dto.request.CreateUserRequest;
import com.scrumsys.userservice.dto.request.UpdateUserRequest;
import com.scrumsys.userservice.service.AdminService;
import com.scrumsys.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor

@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
public class AdminController extends BaseController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> response = adminService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved", response));
    }



    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        UserResponse response = adminService.createUser(request);
        return ResponseEntity.ok(ApiResponse.success("User created", response));
    }


    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUserByAdmin(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return ApiResponse.success(
                "User updated",
                adminService.updateUserByAdmin(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUserById(id);
        return ApiResponse.success("User deleted", null);
    }

    @PostMapping("/{id}/roles")
    public ApiResponse<Void> assignRoles(
            @PathVariable Long id,
            @RequestBody List<Long> roleIds
    ) {
        adminService.assignRoles(id, roleIds);
        return ApiResponse.success("Roles assigned", null);
    }

    @PostMapping("/{id}/department/{deptId}")
    public ApiResponse<Void> assignDepartment(
            @PathVariable Long id,
            @PathVariable Long deptId
    ) {
        adminService.assignDepartment(id, deptId);
        return ApiResponse.success("Department assigned", null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse response = adminService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("User retrieved", response));
    }

}

