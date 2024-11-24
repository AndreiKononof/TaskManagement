package com.example.TaskManagement.validation;

import com.example.TaskManagement.model.Role;
import com.example.TaskManagement.model.enums.RoleType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleEnumValidValidator implements ConstraintValidator<RoleEnumValid, Role> {
    @Override
    public boolean isValid(Role role, ConstraintValidatorContext constraintValidatorContext) {
        if(role.getRole().equals(RoleType.ADMIN) || role.getRole().equals(RoleType.USER)) return true;
        return false;
    }
}
