package com.example.TaskManagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExecutorNameRequest {

    @NotNull(message = "Имя пользователя не может быть пустым!")
    private String name;
}
