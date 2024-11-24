package com.example.TaskManagement.mapper.delegate;

import com.example.TaskManagement.dto.response.UserResponse;
import com.example.TaskManagement.mapper.UserMapper;
import com.example.TaskManagement.model.User;

public abstract class UserDelegate implements UserMapper {

    @Override
    public UserResponse userToUserResponse(User user) {

        UserResponse response = new UserResponse();

        response.setName(user.getName());
        String role = "";
        user.getRoles().forEach(el -> role.concat(el.toString()+"; "));
        response.setRole(role);

        return response;
    }
}
