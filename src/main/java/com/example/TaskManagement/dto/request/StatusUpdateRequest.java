package com.example.TaskManagement.dto.request;

import com.example.TaskManagement.model.enums.StatusTaskType;
import com.example.TaskManagement.validation.EnumNamePattern;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest {

    @EnumNamePattern(enumClass = StatusTaskType.class, enums = "WAITING|IN_PROCESS|EXECUTED")
    @NotNull(message = "statusTaskType должно быть заполнено!")
    private String statusTaskType;
}
