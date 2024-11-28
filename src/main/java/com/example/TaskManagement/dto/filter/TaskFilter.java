package com.example.TaskManagement.dto.filter;


import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class TaskFilter {

    @Positive(message = "ID пользователя не может быть равно или меньше 0!")
    private Long authorId;

    @Positive(message ="ID пользователя не может быть равно или меньше 0!" )
    private Long executorId;

}
