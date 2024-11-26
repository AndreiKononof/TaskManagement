package com.example.TaskManagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumNamePatternValidator.class)
public @interface EnumNamePattern {
    Class<? extends Enum<?>> enumClass();
    String enums();
    String message() default "Введеное значение не соответсвует ожидаемому \"{enums}\"" ;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}