package com.example.TaskManagement.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriorityEnumValidValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PriorityEnumValid {

    String message() default "Введеное значение не соответсвует ожидаемому!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
