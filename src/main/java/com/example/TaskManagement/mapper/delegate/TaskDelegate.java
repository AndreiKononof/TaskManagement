package com.example.TaskManagement.mapper.delegate;

import com.example.TaskManagement.dto.request.TaskRequest;
import com.example.TaskManagement.dto.response.TaskResponse;
import com.example.TaskManagement.dto.response.TaskToListResponse;
import com.example.TaskManagement.mapper.CommentMapper;
import com.example.TaskManagement.mapper.TaskMapper;
import com.example.TaskManagement.model.Priority;
import com.example.TaskManagement.model.StatusTask;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.service.StatusTaskServiceImpl;
import com.example.TaskManagement.service.interfaces.CommentService;
import com.example.TaskManagement.service.interfaces.PriorityService;
import com.example.TaskManagement.service.interfaces.TaskService;
import com.example.TaskManagement.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public abstract class TaskDelegate implements TaskMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private PriorityService priorityService;
    @Autowired
    private StatusTaskServiceImpl statusTaskService;

    @Override
    public Task taskRequestToTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        StatusTask statusTask = statusTaskService.findByStatus(StatusTaskType.WAITING);
        if (statusTask == null) {
            statusTask = new StatusTask();
            statusTask.setStatus(StatusTaskType.WAITING);
            statusTaskService.save(statusTask);
        }
        task.setStatusTask(statusTaskService.findByStatus(StatusTaskType.WAITING));

        Priority priority = priorityService.findByPriority(PriorityType.valueOf(request.getPriorityType()));
        if (priority == null) {
            priority = new Priority();
            priority.setPriorityType(PriorityType.valueOf(request.getPriorityType()));
            priorityService.save(priority);
        }
        task.setPriority(priorityService.findByPriority(PriorityType.valueOf(request.getPriorityType())));

        task.setAuthor(userService.findByName(request.getNameAuthor()));

        List<User> userExecutors = new ArrayList<>();
        request.getNameExecutor().forEach(el ->
                userExecutors.add(userService.findByName(el))
        );

        task.setExecutors(userExecutors);
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
        taskResponse.setStatus(task.getStatusTask().getStatus());
        taskResponse.setPriorityType(task.getPriority().getPriorityType());
        taskResponse.setNameAuthor(task.getAuthor().getName());

        taskResponse.setNameExecutor(task.getExecutors()
                .stream()
                .map(User::getName)
                .toList());

        taskResponse.setComments(commentMapper
                .commentListToCommentListResponse(
                        commentService.findAllByTask(task)
                ));

        return taskResponse;
    }

    @Override
    public List<TaskToListResponse> taskListToListResponse(List<Task> tasks) {

        List<TaskToListResponse> responses = new ArrayList<>();

        for (Task task : tasks) {
            TaskToListResponse taskResponse = new TaskToListResponse();
            taskResponse.setId(task.getId());
            taskResponse.setTitle(task.getTitle());
            taskResponse.setDescription(task.getDescription());
            taskResponse.setStatus(task.getStatusTask().getStatus());
            taskResponse.setPriorityType(task.getPriority().getPriorityType());
            taskResponse.setCommentCount(commentService.findAllByTask(task).size());
            taskResponse.setNameAuthor(task.getAuthor().getName());

            List<String> nameExecutors = new ArrayList<>();
            task.getExecutors().forEach(el ->
                    nameExecutors.add(el.getName())
            );

            taskResponse.setNameExecutor(nameExecutors);
            responses.add(taskResponse);
        }

        return responses;
    }
}
