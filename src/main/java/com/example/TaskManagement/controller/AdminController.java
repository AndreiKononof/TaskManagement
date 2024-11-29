package com.example.TaskManagement.controller;

import com.example.TaskManagement.dto.filter.TaskFilter;
import com.example.TaskManagement.dto.request.*;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Интерфейс администратора v.1", description = "Интерфейс расширяющий возможные операции при работе с приложением пользователю с ролью ADMIN")
public class AdminController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    private final CommentService commentService;
    private final CommentMapper commentMapper;


    @Operation(summary = "Получение задачи по ID", description = "Позволяет получить задачу по ID без привязки к пользователю")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id, PageInfo pageInfo) {
        log.info("Calling request get task admin task ID {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(
                taskMapper.taskToTaskResponse(taskService.findById(id), pageInfo));
    }

    @Operation(
            summary = "Получение всех задач",
            description = "Позволят получить список всех задач. Предусмотрена пагинациия предоставляемого списка." +
                    " Поля ввода валидируются."
    )
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskListResponse> getAllTask(PageInfo pageInfo) {
        log.info("Calling method get all task admin");
        return ResponseEntity.status(HttpStatus.OK).body(
                taskMapper.taskListToTaskListResponse(taskService.findAll(pageInfo)));
    }

    @Operation(
            summary = "Получение всех задач с учетом фильтра",
            description = "Позволят получить список всех задач с учетом фильтра." +
                    " Фильтрация возможна по автору и исполнителю задачи." +
                    " Предусмотрена пагинациия предоставляемого списка. Поля ввода валидируются."
    )
    @GetMapping("/all/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskListResponse> getAllTaskWithFilter(@Valid TaskFilter taskFilter) {
        log.info("Calling method get all task admin with filter");
        return ResponseEntity.status(HttpStatus.OK).body(
                taskMapper.taskListToTaskListResponse(taskService.findAllBy(taskFilter)));
    }

    @Operation(
            summary = "Обновление задачи",
            description = "Позволяет обновить всек поля задачи"
    )
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequest taskRequest) {
        log.info("Calling method update task ID - {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(
                taskMapper.taskToTaskResponse(
                        taskService.update(
                                taskMapper.taskRequestToTask(id, taskRequest)
                        )
                )
        );
    }

    @Operation(
            summary = "Создание коментария",
            description = "Создает коментраий к указанной задаче"
    )
    @PostMapping("/create/comment")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid CommentRequest request) {
        log.info("Calling request create comment {}", request);
        Comment comment = commentService.save(commentMapper.commentRequestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToCommentResponse(comment));
    }

    @Operation(
            summary = "Создание задачи",
            description = "Позволяет создать новую задачу"
    )
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskRequest taskRequest) {
        log.info("Calling request create task");
        return ResponseEntity.status(HttpStatus.CREATED).body(
                taskMapper.taskToTaskResponse(
                        taskService.save(
                                taskMapper.taskRequestToTask(taskRequest)
                        )
                )
        );
    }

    @Operation(
            summary = "Обновление статуса задачи",
            description = "Обновляет статус задачи указанный пользователем. Список возможных статусов ограничен и валидируется"
    )
    @PutMapping("/update_status/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> updateStatusTask(@PathVariable Long taskId, @Valid StatusUpdateRequest request) {
        log.info("Calling request update status task new status - {}", request.getStatusTaskType());
        Task task = taskService.updateStatus(taskId, StatusTaskType.valueOf(request.getStatusTaskType()));
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskToTaskResponse(task));

    }

    @Operation(
            summary = "Обновление приоритета задачи",
            description = "Обновляет приоритет задачи указанный пользователем. Список возможных приоритетов ограничен и валидируется"
    )
    @PutMapping("/update_priority/{taskId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> updatePriority(@PathVariable Long taskId, @Valid PriorityUpdateRequest request) {
        log.info("Calling request update priority task new priority - {}", request.getPriorityType());
        Task task = taskService.updatePriority(taskId, PriorityType.valueOf(request.getPriorityType()));
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskToTaskResponse(task));
    }

    @Operation(
            summary = "Добавление исполнителя задачи",
            description = "Добавляет исполнителя задачи."
    )
    @PutMapping("/add_executor")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TaskResponse> addExecutors(@PathVariable Long taskId, @Valid ExecutorNameRequest request) {
        log.info("Calling request update executors task new executor - {}", request.getName());
        Task task = taskService.addExecutor(taskId, request.getName());
        return ResponseEntity.status(HttpStatus.OK).body(taskMapper.taskToTaskResponse(task));
    }

    @Operation(
            summary = "Удаление задачи по ID",
            description = "Удаляет задачу"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.info("Calling request delete task ID - {}", id);
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
