package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.validations.AllowedDepartment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedDepartmentImpl implements ConstraintValidator<AllowedDepartment, String> {
    @Override
    public void initialize(AllowedDepartment allowedValue) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "WAREHOUSE".equals(value) || "MANUFACTURING".equals(value) || "LAB".equals(value);
    }
}