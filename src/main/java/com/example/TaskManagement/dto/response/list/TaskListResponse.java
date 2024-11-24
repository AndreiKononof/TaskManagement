package com.example.TaskManagement.dto.response.list;

import com.example.TaskManagement.dto.response.TaskToListResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskListResponse {

    private List<TaskToListResponse> tasks = new ArrayList<>();

}
