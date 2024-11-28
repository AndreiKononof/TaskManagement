package com.example.TaskManagement.controller;

import com.example.TaskManagement.exception.*;
import com.example.TaskManagement.dto.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerController {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException e) {
        log.error("Not found exception : {}", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(CreateUserException.class)
    public ResponseEntity<ErrorResponse> notFound(CreateUserException e) {
        log.error("Create user exception : {}", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(ValidTaskException.class)
    public ResponseEntity<ErrorResponse> notFound(ValidTaskException e) {
        log.error("Valid task exception : {}", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validException(MethodArgumentNotValidException ex) {
        log.error("Error in the valid value of the entity field");
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String error = String.join("; ", errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(error));
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ErrorResponse> refreshTokenException(RefreshTokenException e) {
        log.error("Refresh token exception: {}", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(UserNotAllowedCorrectException.class)
    public ResponseEntity<ErrorResponse> refreshTokenException(UserNotAllowedCorrectException e) {
        log.error("Error accessing changes: {}", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

}
