package com.example.TaskManagement.service;

import com.example.TaskManagement.exception.NotFoundException;
import com.example.TaskManagement.model.Priority;
import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.repository.PriorityRepository;
import com.example.TaskManagement.service.interfaces.PriorityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriorityServiceImpl implements PriorityService {
    private final PriorityRepository repository;

    @Override
    public Priority findById(Long id) {
        Priority priority = repository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("Приоритет по ID - {0} не найден", id )));
        log.info("Completed method findById priority ID - {} ", id);
        return priority;
    }

    @Override
    public Priority findByPriority(PriorityType priority) {
        Priority priorityTask = repository.findByPriorityType(priority);
        log.info("Completed method findByPriority priority - {} ", priority);
        return priorityTask;
    }

    @Override
    public Priority save(Priority priority) {
        Priority prioritySave = repository.save(priority);
        log.info("Completed method save priority {}", prioritySave.getPriorityType());
        return priority;
    }
}
