package com.example.TaskManagement.controller;

import com.example.TaskManagement.dto.request.LoginRequest;
import com.example.TaskManagement.dto.request.RefreshTokenRequest;
import com.example.TaskManagement.dto.response.SimpleResponse;
import com.example.TaskManagement.dto.request.UserRequest;
import com.example.TaskManagement.dto.response.AuthResponse;
import com.example.TaskManagement.dto.response.RefreshTokenResponse;
import com.example.TaskManagement.dto.response.UserResponse;
import com.example.TaskManagement.mapper.UserMapper;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.security.SecurityService;
import com.example.TaskManagement.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/public")
@Tag(name = "Интерфейс регистрации v.1", description = "Интерфейс для создания пользователей и получения token")
public class PublicController {


    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Аутентификация пользователя",
            description = "Позволяет аутентифицироваться пользователю по email и паролю и получить token и refresh-token. Поля ввода валидируются"
    )
    @PostMapping("/sign_in")
    public ResponseEntity<AuthResponse> authUser(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(securityService.authenticateUser(request));
    }

    @Operation(
            summary = "Создание и сохранение пользователя в систему",
            description = "Создает пользователя по заданным параметрам с проверкой на уже имеющего пользователя с таким именем и email. Поля ввода валидируются"
    )
    @PutMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid  UserRequest request) {
        log.info("Calling request create user for {}", request);
        securityService.register(request);
        User user = userService.findByName(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToUserResponse(user));
    }

    @Operation(
            summary = "Получение refresh-token",
            description = "Позволяет получить refresh-token"
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(securityService.refreshToken(request));
    }

    @Operation(
            summary = "Вход из системы",
            description = "Позволяет пользователю выйти из системы и удаляет refresh-token из системы"
    )
    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logout(@AuthenticationPrincipal UserDetails userDetails) {
        securityService.logout();
        return ResponseEntity.status(HttpStatus.OK).body
                (new SimpleResponse("Пользователь " + userDetails.getUsername() + " вышел из системы"));
    }


}
