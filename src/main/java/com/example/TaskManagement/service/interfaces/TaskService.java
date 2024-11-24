package com.example.TaskManagement.service.interfaces;

import com.example.TaskManagement.model.Task;

import java.util.List;

public interface TaskService {


    Task findById(Long id);

    List<Task> findAll();

    List<Task> findAllBy();

    Task save(Task task);

    Task update(Task task);

    void delete(Long id);
}
