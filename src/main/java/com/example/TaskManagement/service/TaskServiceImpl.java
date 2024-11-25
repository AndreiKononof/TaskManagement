package com.example.TaskManagement.service;


import com.example.TaskManagement.exception.NotFoundException;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.repository.TaskRepository;
import com.example.TaskManagement.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(MessageFormat.format("Задача с ID - {0} не найден", id)));
        log.info("Completed method findById task ID - {}", id);
        return task;
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = taskRepository.findAll();
        log.info("Completed method findAll task");
        return tasks;
    }

    @Override
    public List<Task> findAllBy() {
        return List.of();
    }

    @Override
    public Task save(Task task) {
        Task taskSave = taskRepository.save(task);
        log.info("Save task ID - {}",task.getId());
        return taskSave;
    }

    @Override
    public Task update(Task task) {
        Task taskUpdate = taskRepository.save(task);
        log.info("Update task ID - {}",task.getId());
        return taskUpdate;
    }

    @Override
    public void delete(Long id) {
    taskRepository.deleteById(id);
    log.info("Delete task ID - {}",id);
    }
}
