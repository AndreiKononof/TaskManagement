package com.example.TaskManagement.dto.response;

import com.example.TaskManagement.dto.response.list.CommentListResponse;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private StatusTaskType status;

    private PriorityType priority;

    private CommentListResponse comments;

    private String nameAuthor;

    private List<String> nameExecutor;
}
