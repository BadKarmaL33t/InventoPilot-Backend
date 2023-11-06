package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.validations.AllowedDepartment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedStatusImpl implements ConstraintValidator<AllowedDepartment, String> {
    @Override
    public void initialize(AllowedDepartment allowedValue) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "AWAITING_MATERIALS".equals(value) ||
                "IN_STOCK".equals(value) ||
                "IN_PRODUCTION".equals(value) ||
                "BATCH_TESTING".equals(value) ||
                "SHIPPING".equals(value);
    }
}