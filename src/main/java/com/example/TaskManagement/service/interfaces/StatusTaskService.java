package com.example.TaskManagement.service.interfaces;

import com.example.TaskManagement.model.StatusTask;
import com.example.TaskManagement.model.enums.StatusTaskType;

public interface StatusTaskService {

    StatusTask findById(Long id);

    StatusTask findByStatus(StatusTaskType status);

    StatusTask save(StatusTask statusTask);
}
