package com.example.TaskManagement.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentRequest {

    @NotNull(message = "Текст комментария пустой")
    private String text;

    @NotNull(message = "Имя пользователя не заполнено!")
    private String user;

    @NotNull(message = "ID задачи не заполнен!")
    @Positive(message = "ID задачи не может быть меньше или равно 0!")
    private Long taskId;

}
