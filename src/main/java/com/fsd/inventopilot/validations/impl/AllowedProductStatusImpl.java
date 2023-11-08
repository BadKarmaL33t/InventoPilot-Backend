package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.validations.AllowedDepartment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedProductStatusImpl implements ConstraintValidator<AllowedDepartment, String> {
    @Override
    public void initialize(AllowedDepartment allowedValue) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return
                "IN_STOCK".equals(value) ||
                "OUT_OF_STOCK".equals(value) ||
                "AWAITING_BACKORDER".equals(value);
    }
}