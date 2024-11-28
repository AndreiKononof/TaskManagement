package com.example.TaskManagement.controller;

import com.example.TaskManagement.dto.request.CommentRequest;
import com.example.TaskManagement.dto.request.ExecutorNameRequest;
import com.example.TaskManagement.dto.request.StatusUpdateRequest;
import com.example.TaskManagement.dto.response.CommentResponse;
import com.example.TaskManagement.dto.response.TaskResponse;
import com.example.TaskManagement.dto.response.list.TaskListResponse;
import com.example.TaskManagement.mapper.CommentMapper;
import com.example.TaskManagement.mapper.TaskMapper;
import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.model.pagination.PageInfo;
import com.example.TaskManagement.security.SecurityService;
import com.example.TaskManagement.service.interfaces.CommentService;
import com.example.TaskManagement.service.interfaces.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final SecurityService securityService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id,String name, PageInfo pageInfo){
        log.info("Calling request get task ID - {}", id);
        Task task = taskService.findByUser(id,name);
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskToTaskResponse(task));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<TaskListResponse> getAllTask(@RequestBody ExecutorNameRequest request, PageInfo pageInfo){
        log.info("Calling request get all task");
        List<Task> tasks = taskService.findAllByUser(request.getName(), pageInfo);
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskListToTaskListResponse(tasks));
    }

    @PutMapping("/create/comment/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long id,@RequestBody @Valid CommentRequest request){
        log.info("Calling request create comment {}",request);
        Comment comment = commentService.save(commentMapper.commentRequestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToCommentResponse(comment));
    }

    @PostMapping("/update_status/{taskId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> updateStatusTask(@PathVariable Long taskId, @RequestBody @Valid StatusUpdateRequest status){
        log.info("Calling request update status task new status - {}", status.getStatusTaskType());
        Task task = taskService.updateStatus(taskId, StatusTaskType.valueOf(status.getStatusTaskType()));
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskToTaskResponse(task));

    }



}
