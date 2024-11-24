package com.example.TaskManagement.dto.response;

import com.example.TaskManagement.dto.response.list.CommentListResponse;
import lombok.Data;

@Data
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private String status;

    private String priority;

    private CommentListResponse comments;

    private String nameAuthor;

    private String nameExecutor;
}
