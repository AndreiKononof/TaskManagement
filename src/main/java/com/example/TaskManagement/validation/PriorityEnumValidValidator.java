package com.example.TaskManagement.validation;

import com.example.TaskManagement.model.enums.Priority;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityEnumValidValidator implements ConstraintValidator<PriorityEnumValid, Priority> {
    @Override
    public boolean isValid(Priority priority, ConstraintValidatorContext constraintValidatorContext) {
        if (priority.equals(Priority.HIGH) ||
                priority.equals(Priority.LOW) ||
                priority.equals(Priority.MEDIUM)) return true;
        return false;
    }
}
