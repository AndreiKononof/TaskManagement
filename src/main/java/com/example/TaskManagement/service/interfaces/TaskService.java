package com.example.TaskManagement.service.interfaces;

import com.example.TaskManagement.dto.filter.TaskFilter;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.model.pagination.PageInfo;

import java.util.List;

public interface TaskService {


    Task findById(Long id);

    Task findByUser(Long id, String name);

    List<Task> findAll(PageInfo pageInfo);

    List<Task> findAllByUser(String name, PageInfo pageInfo);

    List<Task> findAllBy(TaskFilter taskFilter);

    Task save(Task task);

    Task update(Task task);

    Task updateStatus(Long id, StatusTaskType statusTaskType);

    Task updatePriority(Long id, PriorityType priorityType);

    Task addExecutor(Long id, String name);

    void delete(Long id);
}
