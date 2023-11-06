package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.validations.AllowedRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedRoleImpl implements ConstraintValidator<AllowedRole, String> {
    @Override
    public void initialize(AllowedRole allowedValue) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "USER".equals(value) || "ADMIN".equals(value) || "SUPERUSER".equals(value);
    }
}