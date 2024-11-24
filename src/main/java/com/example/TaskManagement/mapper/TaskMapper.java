package com.example.TaskManagement.mapper;


import com.example.TaskManagement.dto.request.TaskRequest;
import com.example.TaskManagement.dto.response.TaskResponse;
import com.example.TaskManagement.dto.response.TaskToListResponse;
import com.example.TaskManagement.dto.response.list.TaskListResponse;
import com.example.TaskManagement.mapper.delegate.TaskDelegate;
import com.example.TaskManagement.model.Task;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(TaskDelegate.class)
@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {CommentMapper.class, UserMapper.class})
public interface TaskMapper {

    Task taskRequestToTask(TaskRequest request);

    Task taskRequestToTask(Long id, TaskRequest request);

    TaskResponse taskToTaskResponse(Task task);

    List<TaskToListResponse> taskListToListResponse(List<Task> tasks);

    default TaskListResponse taskListToTaskListResponse(List<Task> tasks) {
        TaskListResponse response = new TaskListResponse();
        response.setTasks(taskListToListResponse(tasks));
        return response;
    }
}
