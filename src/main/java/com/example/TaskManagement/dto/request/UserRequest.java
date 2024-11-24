package com.example.TaskManagement.dto.request;


import com.example.TaskManagement.validation.RoleEnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Имя пользователя не заполнено!")
    private String name;

    @NotBlank(message = "Пароль не заполнен!")
    private String password;

    @NotBlank(message = "Роль пользователя не задана!")
    @RoleEnumValid
    private String role;



}
