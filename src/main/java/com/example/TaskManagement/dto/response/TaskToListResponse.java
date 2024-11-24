package com.example.TaskManagement.dto.response;

import lombok.Data;

@Data
public class TaskToListResponse {

    private Long id;

    private String title;

    private String description;

    private String status;

    private String priority;

    private Integer commentCount;

    private String nameAuthor;

    private String nameExecutor;
}
