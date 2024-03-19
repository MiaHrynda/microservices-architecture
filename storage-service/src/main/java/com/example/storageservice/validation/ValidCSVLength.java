package com.example.storageservice.validation;

import com.example.storageservice.validation.constraint.CSVLengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.PARAMETER)
@Constraint(validatedBy = CSVLengthValidator.class)
public @interface ValidCSVLength {
    String message() default "There are too many ids. CSV string length is longer than expected.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
