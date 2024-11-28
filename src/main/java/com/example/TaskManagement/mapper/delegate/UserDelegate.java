package com.example.TaskManagement.mapper.delegate;

import com.example.TaskManagement.dto.request.UserRequest;
import com.example.TaskManagement.dto.response.UserResponse;
import com.example.TaskManagement.mapper.UserMapper;
import com.example.TaskManagement.model.Role;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.model.enums.RoleType;
import com.example.TaskManagement.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UserDelegate implements UserMapper {

    @Autowired
    private RoleService roleService;


    @Override
    public User userRequestToUser(UserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());

        Role role = roleService.findByRole(RoleType.valueOf(request.getRole()));
        if(role == null){
            Role newRole = new Role();
            newRole.setRole(RoleType.valueOf(request.getRole()));
            role = roleService.save(newRole);
        }
        user.setRole(role);

        return user;
    }

    @Override
    public UserResponse userToUserResponse(User user) {

        UserResponse response = new UserResponse();

        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().toString());

        return response;
    }
}
