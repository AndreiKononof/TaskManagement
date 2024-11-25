package com.example.TaskManagement.validation;

import com.example.TaskManagement.model.enums.StatusTaskType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusEnumValidValidator implements ConstraintValidator<StatusEnumValid, StatusTaskType> {
    @Override
    public boolean isValid(StatusTaskType statusTask, ConstraintValidatorContext constraintValidatorContext) {
        if (statusTask.equals(StatusTaskType.EXECUTED) ||
                statusTask.equals(StatusTaskType.IN_PROCESS) ||
                statusTask.equals(StatusTaskType.WAITING)
        ) return true;
        return false;
    }
}
