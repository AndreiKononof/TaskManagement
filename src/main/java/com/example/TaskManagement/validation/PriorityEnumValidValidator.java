package com.example.TaskManagement.validation;

import com.example.TaskManagement.model.enums.PriorityType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityEnumValidValidator implements ConstraintValidator<PriorityEnumValid, PriorityType> {
    @Override
    public boolean isValid(PriorityType priority, ConstraintValidatorContext constraintValidatorContext) {
        if (priority.equals(PriorityType.HIGH) ||
                priority.equals(PriorityType.LOW) ||
                priority.equals(PriorityType.MEDIUM)) return true;
        return false;
    }
}
