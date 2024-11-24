package com.example.TaskManagement.controller;


import com.example.TaskManagement.dto.request.UserRequest;
import com.example.TaskManagement.dto.response.UserResponse;
import com.example.TaskManagement.mapper.UserMapper;
import com.example.TaskManagement.model.User;
import com.example.TaskManagement.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicController {


    private final UserService userService;
    private final UserMapper userMapper;


    @PutMapping("/create")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request){
        log.info("Calling request create user for {}", request);
        User user = userService.save(userMapper.userRequestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToUserResponse(user));
    }

}
