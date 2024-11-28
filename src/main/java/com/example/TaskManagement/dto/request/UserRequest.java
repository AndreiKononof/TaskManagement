package com.example.TaskManagement.dto.request;


import com.example.TaskManagement.model.enums.RoleType;
import com.example.TaskManagement.validation.EnumNamePattern;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.ls.LSInput;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "Имя пользователя не заполнено!")
    private String name;

    @NotBlank(message = "Пароль не заполнен!")
    private String password;

    @NotNull(message = "Поле email должно быть заполнено!")
    @Email
    private String email;

    @NotNull(message = "Роль пользователя не задана!")
    @EnumNamePattern(enumClass = RoleType.class, enums = "USER|ADMIN")
    private String role;



}
