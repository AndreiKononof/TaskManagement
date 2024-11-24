package com.example.TaskManagement.dto.response.list;

import com.example.TaskManagement.dto.response.CommentResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentListResponse {

    private List<CommentResponse> comments = new ArrayList<>();

}
