package com.example.TaskManagement.exception;

public class UserNotAllowedCorrectException extends RuntimeException {
    public UserNotAllowedCorrectException(String message) {
        super(message);
    }
}
