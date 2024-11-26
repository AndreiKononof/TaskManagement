package com.example.TaskManagement.service;


import com.example.TaskManagement.exception.NotFoundException;
import com.example.TaskManagement.exception.ValidTaskException;
import com.example.TaskManagement.model.Priority;
import com.example.TaskManagement.model.StatusTask;
import com.example.TaskManagement.model.Task;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.model.pagination.PageInfo;
import com.example.TaskManagement.repository.TaskRepository;
import com.example.TaskManagement.service.interfaces.PriorityService;
import com.example.TaskManagement.service.interfaces.StatusTaskService;
import com.example.TaskManagement.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final StatusTaskService statusTaskService;

    private final PriorityService priorityService;

    @Override
    public Task findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException(MessageFormat.format("Задача с ID - {0} не найден", id)));
        log.info("Completed method findById task ID - {}", id);
        return task;
    }


    @Override
    public Task findByUser(Long id, String name) {
        Task task = findById(id);

        if(task.getExecutors().stream().map(User::getName).toList().contains(name)){
            log.info("Completed method find task user {}",name);
            return task;
        }

        throw new ValidTaskException("Вы не являетесь испольнителем задачи, ее просмотр не доступен!");
    }

    @Override
    public List<Task> findAll(PageInfo pageInfo) {
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }
        List<Task> tasks = taskRepository.findAll(PageRequest.of(pageInfo.getNumber(), pageInfo.getSize())).getContent();
        log.info("Completed method findAll task");
        return tasks;
    }

    @Override
    public List<Task> findAllByUser(String name, PageInfo pageInfo) {
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }
        List<Task> tasksAll = taskRepository.findAll(PageRequest.of(pageInfo.getNumber(), pageInfo.getSize())).getContent();
        List<Task> tasks = new ArrayList<>();
        for(Task task : tasksAll){
            if(task.getExecutors().stream().map(User::getName).toList().contains(name)){
                tasks.add(task);
            }
        }
        log.info("Completed method find task for user {}",name);
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
    @Transactional
    public Task updateStatus(Long id, StatusTaskType statusTaskType) {
        Task task = findById(id);
        StatusTask statusTask = statusTaskService.findByStatus(statusTaskType);
        if(statusTask == null){
            statusTask =new StatusTask();
            statusTask.setStatus(statusTaskType);
            statusTaskService.save(statusTask);
        }
        task.setStatusTask(statusTask);
        save(task);
        log.info("Completed method update status task new status - {}", statusTaskType);
        return task;
    }

    @Override
    @Transactional
    public Task updatePriority(Long id, PriorityType priorityType) {
        Task task = findById(id);
        Priority priority = priorityService.findByPriority(priorityType);
        if(priority == null){
            priority = new Priority();
            priority.setPriorityType(priorityType);
            priorityService.save(priority);
        }
        task.setPriority(priority);
        save(task);
        log.info("Completed method update status task new status - {}", priorityType);
        return task;
    }

    @Override
    public void delete(Long id) {
    taskRepository.deleteById(id);
    log.info("Delete task ID - {}",id);
    }
}
