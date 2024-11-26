package com.example.TaskManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ExecutorUpdateRequest {

    @NotBlank(message = "Список испольнителей не может быть пустой")
    private List<String> executor;
}
