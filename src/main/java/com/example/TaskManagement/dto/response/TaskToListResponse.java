package com.example.TaskManagement.dto.response;

import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import lombok.Data;

import java.util.List;

@Data
public class TaskToListResponse {

    private Long id;

    private String title;

    private String description;

    private StatusTaskType status;

    private PriorityType priorityType;

    private Integer commentCount;

    private String nameAuthor;

    private List<String> nameExecutor;
}
