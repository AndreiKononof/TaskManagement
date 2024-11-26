package com.example.TaskManagement.dto.request;

import com.example.TaskManagement.model.enums.PriorityType;

import com.example.TaskManagement.validation.EnumNamePattern;
import lombok.Data;

@Data
public class PriorityUpdateRequest {

    @EnumNamePattern(enumClass = PriorityType.class, enums = "LOW|MEDIUM|HIGH}")
    private String priorityType;

}
