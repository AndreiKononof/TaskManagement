package com.example.TaskManagement.validation;

import com.example.TaskManagement.model.enums.StatusTask;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusEnumValidValidator implements ConstraintValidator<StatusEnumValid, StatusTask> {
    @Override
    public boolean isValid(StatusTask statusTask, ConstraintValidatorContext constraintValidatorContext) {
        if (statusTask.equals(StatusTask.EXECUTED) ||
                statusTask.equals(StatusTask.IN_PROCESS) ||
                statusTask.equals(StatusTask.WAITING)
        ) return true;
        return false;
    }
}
