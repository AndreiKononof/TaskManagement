package com.example.TaskManagement.service.interfaces;

import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.StatusTaskType;

import java.util.List;

public interface TaskService {


    Task findById(Long id);

    Task findByUser(Long id, String name);

    List<Task> findAll();

    List<Task> findAllByUser(String name);

    List<Task> findAllBy();

    Task save(Task task);

    Task update(Task task);

    Task updateStatus(Long id, StatusTaskType statusTaskType);

    Task updatePriority(Long id, PriorityType priorityType);

    void delete(Long id);
}
