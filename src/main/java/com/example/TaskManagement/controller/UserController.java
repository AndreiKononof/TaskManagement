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
import com.example.TaskManagement.service.interfaces.CommentService;
import com.example.TaskManagement.service.interfaces.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Интерфейс пользователя v.1",description = "Интерфейс позволяющий пользователю с ролью USER совершать операции")
public class UserController {


    private final TaskService taskService;

    private final TaskMapper taskMapper;

    private final CommentMapper commentMapper;

    private final CommentService commentService;

    @Operation(summary = "Получение задачи по ID",
            description = "Позволяет получать задачу пользователю по ID при условии, что пользователь являеться исполнителем задачи. Поля ввода валидируются."
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Calling request get task ID - {}", id);
        Task task = taskService.findByUser(id, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskToTaskResponse(task));
    }

    @Operation(
            summary = "Получение всех задач пользователя",
            description = "Позволят получить список всех задач пользователя. Предусмотрена пагинациия предоставляемого списка. Поля ввода валидируются."
    )
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<TaskListResponse> getAllTask(@AuthenticationPrincipal UserDetails userDetails, PageInfo pageInfo) {
        log.info("Calling request get all task");
        List<Task> tasks = taskService.findAllByUser(userDetails.getUsername(), pageInfo);
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskListToTaskListResponse(tasks));
    }

    @Operation(
            summary = "Создание комментария к задаче",
            description = "Позволяет создать и добавить коментарий к выполняемой задачи. Поля ввода валидируются."
    )
    @PostMapping("/create/comment")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid CommentRequest request) {
        log.info("Calling request create comment {}", request);
        Comment comment = commentService.save(commentMapper.commentRequestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToCommentResponse(comment));
    }

    @Operation(
            summary = "Обновление статуса задачи",
            description = "Обновляет статус задачи указанный пользователем. Список возможных статусов ограничен и валидируется"
    )
    @PutMapping("/update_status/{taskId}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> updateStatusTask(@PathVariable Long taskId, @RequestBody @Valid StatusUpdateRequest status) {
        log.info("Calling request update status task new status - {}", status.getStatusTaskType());
        Task task = taskService.updateStatus(taskId, StatusTaskType.valueOf(status.getStatusTaskType()));
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskToTaskResponse(task));
    }


}
