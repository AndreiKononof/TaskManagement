package com.example.TaskManagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentRequest {

    @NotBlank(message = "Текст комментария пустой")
    private String text;

    @NotBlank(message = "Имя пользователя не заполнено!")
    private String user;

}
