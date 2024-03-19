package com.example.resourceservice.validation.constraint;

import com.example.resourceservice.validation.ValidCSVLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class CSVLengthValidator implements ConstraintValidator<ValidCSVLength, String> {
    private static final int MAX_CSV_LENGTH = 200;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.length() < MAX_CSV_LENGTH;
    }
}
