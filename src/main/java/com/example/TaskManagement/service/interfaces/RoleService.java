package com.example.TaskManagement.service.interfaces;

import com.example.TaskManagement.model.Role;
import com.example.TaskManagement.model.enums.RoleType;


public interface RoleService {

    Role save(Role role);

    Role findById(Long id);

    Role findByRole(RoleType role);

}
