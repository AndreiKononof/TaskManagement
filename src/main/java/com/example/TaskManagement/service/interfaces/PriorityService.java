package com.example.TaskManagement.service.interfaces;

import com.example.TaskManagement.model.Priority;
import com.example.TaskManagement.model.enums.PriorityType;

public interface PriorityService {
    Priority findById(Long id);

    Priority findByPriority(PriorityType priority);

    Priority save(Priority priority);
}
