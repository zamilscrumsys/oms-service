package com.scrumsys.userservice.util;

import com.scrumsys.common.constants.AppConstants;
import com.scrumsys.common.exception.ApplicationException;
import com.scrumsys.userservice.entity.Department;
import com.scrumsys.userservice.entity.Function;
import com.scrumsys.userservice.entity.Role;
import com.scrumsys.userservice.entity.User;
import com.scrumsys.userservice.repository.DepartmentRepository;
import com.scrumsys.userservice.repository.FunctionRepository;
import com.scrumsys.userservice.repository.RoleRepository;
import com.scrumsys.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class Helper {
    private  final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final FunctionRepository functionRepository;

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

    public Role createDefaultRole() {
        Role role = new Role();
        role.setName(AppConstants.ROLE_USER);
        role.setDescription("Default user role");
        role.setCreatedBy("SYSTEM");

        // Add default functions
        Set<Function> defaultFunctions = new HashSet<>();
        functionRepository.findByNameIn(List.of("VIEW_PROFILE", "EDIT_PROFILE"))
                .forEach(defaultFunctions::add);

        role.setFunctions(defaultFunctions);
        return roleRepository.save(role);
    }
}
