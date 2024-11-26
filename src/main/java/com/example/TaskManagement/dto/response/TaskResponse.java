package com.example.TaskManagement.dto.response;

import com.example.TaskManagement.dto.response.list.CommentListResponse;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import lombok.Data;

import java.util.List;

@Data
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private StatusTaskType status;

    private PriorityType priorityType;

    private CommentListResponse comments;

    private String nameAuthor;

    private List<String> nameExecutor;
}
