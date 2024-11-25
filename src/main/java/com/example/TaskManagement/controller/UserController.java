package com.example.TaskManagement.controller;

import com.example.TaskManagement.dto.request.CommentRequest;
import com.example.TaskManagement.dto.response.CommentResponse;
import com.example.TaskManagement.dto.response.TaskResponse;
import com.example.TaskManagement.dto.response.list.TaskListResponse;
import com.example.TaskManagement.mapper.CommentMapper;
import com.example.TaskManagement.mapper.TaskMapper;
import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.service.interfaces.CommentService;
import com.example.TaskManagement.service.interfaces.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {


    private final TaskService taskService;

    private final TaskMapper taskMapper;

    private final CommentMapper commentMapper;

    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(Long id,String name){
        Task task = taskService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskToTaskResponse(task));
    }

    @GetMapping("/all")
    public ResponseEntity<TaskListResponse> getAllTask(String name){
        List<Task> tasks = taskService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskListToTaskListResponse(tasks));
    }

    @PutMapping("/create/comment/{id}")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long id, @Valid CommentRequest request){
        Comment comment = commentService.save(commentMapper.commentRequestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToCommentResponse(comment));
    }





}
