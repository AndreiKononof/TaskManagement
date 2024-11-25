package com.example.TaskManagement.dto.request;

import com.example.TaskManagement.model.enums.PriorityType;
import com.example.TaskManagement.validation.PriorityEnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequest {

    @NotBlank(message = "Заголовок задачи пустой!")
    private String title;

    @NotBlank(message = "Задача не описана!")
    private String description;

    @NotBlank(message = "Задаче не задан приоритет!")
    @PriorityEnumValid
    private PriorityType priority;

    @NotBlank(message = "Автор задачи не заполнен!")
    private String nameAuthor;

    @NotBlank(message = "не назначен исполнитель!")
    private String nameExecutor;




}
