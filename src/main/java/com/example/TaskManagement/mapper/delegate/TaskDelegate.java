package com.example.TaskManagement.mapper.delegate;

import com.example.TaskManagement.dto.request.TaskRequest;
import com.example.TaskManagement.dto.response.TaskResponse;
import com.example.TaskManagement.dto.response.TaskToListResponse;
import com.example.TaskManagement.mapper.CommentMapper;
import com.example.TaskManagement.mapper.TaskMapper;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.service.interfaces.CommentService;
import com.example.TaskManagement.service.interfaces.TaskService;
import com.example.TaskManagement.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class TaskDelegate implements TaskMapper {

    private final UserService userService;

    private final TaskService taskService;

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @Override
    public Task taskRequestToTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatusTask(StatusTaskType.WAITING);
        task.setPriority(request.getPriority());
        task.setAuthor(userService.findByName(request.getNameAuthor()));
        task.setExecutor(userService.findByName(request.getNameExecutor()));
        return task;
    }

    @Override
    public Task taskRequestToTask(Long id, TaskRequest request) {
        Task taskDB = taskService.findById(id);
        Task updateTask = taskRequestToTask(request);
        updateTask.setId(taskDB.getId());
        updateTask.setStatusTask(taskDB.getStatusTask());
        updateTask.setComments(taskDB.getComments());
        return updateTask;
    }

    @Override
    public TaskResponse taskToTaskResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setStatus(task.getStatusTask().toString());
        taskResponse.setPriority(task.getPriority().toString());
        taskResponse.setComments(commentMapper
                .commentListToCommentListResponse(
                        commentService.findAllByTask(task)
                ));

        return null;
    }

    @Override
    public List<TaskToListResponse> taskListToListResponse(List<Task> tasks) {

        List<TaskToListResponse> responses = new ArrayList<>();

        for (Task task : tasks){
            TaskToListResponse taskResponse = new TaskToListResponse();
            taskResponse.setId(task.getId());
            taskResponse.setTitle(task.getTitle());
            taskResponse.setDescription(task.getDescription());
            taskResponse.setStatus(task.getStatusTask().toString());
            taskResponse.setPriority(task.getPriority().toString());
            taskResponse.setCommentCount(commentService.findAllByTask(task).size());
            taskResponse.setNameAuthor(task.getAuthor().getName());
            taskResponse.setNameExecutor(task.getExecutor().getName());
            responses.add(taskResponse);
        }

        return responses;
    }
}
