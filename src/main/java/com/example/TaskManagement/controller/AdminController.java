package com.example.TaskManagement.controller;


import com.example.TaskManagement.dto.request.CommentRequest;
import com.example.TaskManagement.dto.request.PriorityUpdateRequest;
import com.example.TaskManagement.dto.request.StatusUpdateRequest;
import com.example.TaskManagement.dto.request.TaskRequest;
import com.example.TaskManagement.dto.response.CommentResponse;
import com.example.TaskManagement.dto.response.TaskResponse;
import com.example.TaskManagement.dto.response.list.TaskListResponse;
import com.example.TaskManagement.mapper.CommentMapper;
import com.example.TaskManagement.mapper.TaskMapper;
import com.example.TaskManagement.model.Comment;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.model.pagination.PageInfo;
import com.example.TaskManagement.service.interfaces.CommentService;
import com.example.TaskManagement.service.interfaces.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    private final CommentService commentService;
    private final CommentMapper commentMapper;


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id,PageInfo pageInfo) {
        log.info("Calling request get task admin task ID {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(
                taskMapper.taskToTaskResponse(taskService.findById(id),pageInfo));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskListResponse> getAllTask(PageInfo pageInfo) {
        log.info("Calling method get all task admin");
        return ResponseEntity.status(HttpStatus.OK).body(
                taskMapper.taskListToTaskListResponse(taskService.findAll(pageInfo)));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequest taskRequest){
        log.info("Calling method update task ID - {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(
                taskMapper.taskToTaskResponse(
                        taskService.update(
                                taskMapper.taskRequestToTask(id,taskRequest)
                        )
                )
        );
    }

    @PutMapping("/create/comment")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CommentResponse> createComment( @Valid @RequestBody CommentRequest request){
        log.info("Calling request create comment {}",request);
        Comment comment = commentService.save(commentMapper.commentRequestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToCommentResponse(comment));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest) {
        log.info("Calling request create task");
        return ResponseEntity.status(HttpStatus.CREATED).body(
                taskMapper.taskToTaskResponse(
                        taskService.save(
                                taskMapper.taskRequestToTask(taskRequest)
                        )
                )
        );
    }

    @PostMapping("/update_status/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> updateStatusTask(@PathVariable Long taskId, @Valid StatusUpdateRequest request){
        log.info("Calling request update status task new status - {}", request.getStatusTaskType());
        Task task = taskService.updateStatus(taskId, StatusTaskType.valueOf(request.getStatusTaskType()));
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskToTaskResponse(task));

    }

    @PostMapping("/update_priority/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> updatePriority(@PathVariable Long taskId, @Valid PriorityUpdateRequest request){
        log.info("Calling request update priority task new priority - {}", request.getPriorityType());
        Task task = taskService.updatePriority(taskId, PriorityType.valueOf(request.getPriorityType()));
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskToTaskResponse(task));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        log.info("Calling request delete task ID - {}",id);
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
