package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.services.AllowedValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValueValidatorImpl implements ConstraintValidator<AllowedValue, String> {
    @Override
    public void initialize(AllowedValue allowedValue) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "USER".equals(value) || "ADMIN".equals(value) || "SUPERUSER".equals(value);
    }
}