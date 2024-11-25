package com.example.TaskManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentRequest {

    @NotBlank(message = "Текст комментария пустой")
    private String text;

    @NotBlank(message = "Имя пользователя не заполнено!")
    private String user;

    @NotBlank(message = "ID задачи не заполнен!")
    @Positive(message = "ID задачи не может быть меньше или равно 0!")
    private Long taskId;

}
