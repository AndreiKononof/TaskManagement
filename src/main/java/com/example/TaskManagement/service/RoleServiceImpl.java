package com.example.TaskManagement.service;

import com.example.TaskManagement.exception.NotFoundException;
import com.example.TaskManagement.model.Role;
import com.example.TaskManagement.model.enums.RoleType;
import com.example.TaskManagement.repository.RoleRepository;
import com.example.TaskManagement.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role save(Role role) {
        Role newRole = repository.save(role);
        log.info("Completed method save role {}", role.getRole());
        return newRole;
    }

    @Override
    public Role findById(Long id) {
        Role role = repository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("Роль с ID {0} не найдена", id)));
        log.info("Completed method find by id role - {}",id);
        return role;
    }

    @Override
    public Role findByRole(RoleType role) {
        Role roleDB = repository.findByRole(role);
        if(roleDB == null){
            roleDB = new Role();
            roleDB.setRole(role);
            roleDB = save(roleDB);
        }
        log.info("Completed method find role {}", role);
        return roleDB;
    }
}
