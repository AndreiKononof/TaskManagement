package com.example.TaskManagement.repository;

import com.example.TaskManagement.model.Role;
import com.example.TaskManagement.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(RoleType roleType);
}
