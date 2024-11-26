package com.example.TaskManagement.dto.request;

import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.validation.EnumNamePattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TaskRequest {

    @NotBlank(message = "Заголовок задачи пустой!")
    private String title;

    @NotBlank(message = "Задача не описана!")
    private String description;

    @NotNull(message = "Приоритет должен быть заполнен")
    @EnumNamePattern(enumClass = PriorityType.class, enums = "LOW|MEDIUM|HIGH")
    private String priorityType;

    @NotBlank(message = "Автор задачи не заполнен!")
    private String nameAuthor;

    @NotNull(message = "Не назначен исполнитель!")
    private List<String> nameExecutor;

}
