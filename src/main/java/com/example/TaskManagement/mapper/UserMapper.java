package com.example.TaskManagement.mapper;


import com.example.TaskManagement.dto.request.UserRequest;
import com.example.TaskManagement.dto.response.UserResponse;
import com.example.TaskManagement.dto.response.list.UserListResponse;
import com.example.TaskManagement.mapper.delegate.UserDelegate;
import com.example.TaskManagement.model.Role;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.service.interfaces.RoleService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DecoratedWith(UserDelegate.class)
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {


    User userRequestToUser (UserRequest request);

    User userRequestToUser (Long id, UserRequest request);

    UserResponse userToUserResponse (User user);

    List<UserResponse> userListToListResponse (List<User> users);


    default UserListResponse userListToUserListResponse(List<User> users){
        UserListResponse response = new UserListResponse();

        response.setUsers(userListToListResponse(users));

        return response;
    }

    default String map(Role role){
        return role.getRole().toString();
    }



}
