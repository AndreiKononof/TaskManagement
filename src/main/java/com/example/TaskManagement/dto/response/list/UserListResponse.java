package com.example.TaskManagement.dto.response.list;

import com.example.TaskManagement.dto.response.UserResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserListResponse {

    private List<UserResponse> users = new ArrayList<>();

}
