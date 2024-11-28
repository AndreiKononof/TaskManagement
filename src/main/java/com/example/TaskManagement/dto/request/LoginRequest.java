package com.example.TaskManagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "Поле email не может быть пустым!")
    private String email;
    @NotNull(message = "Поле password не может быть пустым!")
    private String password;
}
