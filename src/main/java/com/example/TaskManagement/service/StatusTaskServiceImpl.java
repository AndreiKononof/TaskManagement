package com.example.TaskManagement.service;

import com.example.TaskManagement.exception.NotFoundException;
import com.example.TaskManagement.model.StatusTask;
import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.repository.StatusTaskRepository;
import com.example.TaskManagement.service.interfaces.StatusTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatusTaskServiceImpl implements StatusTaskService {

    private final StatusTaskRepository statusTaskRepository;


    @Override
    public StatusTask findById(Long id) {
        StatusTask statusTask = statusTaskRepository.findById(id).orElseThrow(() ->
                new NotFoundException(MessageFormat.format("Статус с ID - {0} не найден ", id)));
        log.info("Completed method findById statusTask ID - {}", id);
        return statusTask;
    }

    @Override
    public StatusTask findByStatus(StatusTaskType status) {
        StatusTask statusTask = statusTaskRepository.findByStatusType(status);
        log.info("Completed method findById statusTask ID - {}", status);
        return statusTask;
    }

    @Override
    public StatusTask save(StatusTask statusTask) {
        StatusTask statusTaskSave = statusTaskRepository.save(statusTask);
        log.info("Completed method save status {}",statusTaskSave.getStatus());
        return statusTaskSave;
    }
}
