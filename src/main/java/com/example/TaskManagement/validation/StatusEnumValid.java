package com.example.TaskManagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StatusEnumValidValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusEnumValid {

    String message() default "Введеное значение не соответсвует ожидаемому!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
