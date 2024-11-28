package com.example.TaskManagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ExecutorNameListRequest {

    @NotNull(message = "Список испольнителей не может быть пустой")
    private List<String> executor;
}
