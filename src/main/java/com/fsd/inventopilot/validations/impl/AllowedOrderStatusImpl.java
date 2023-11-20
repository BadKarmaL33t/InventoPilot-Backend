package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.validations.AllowedOrderStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedOrderStatusImpl implements ConstraintValidator<AllowedOrderStatus, String> {
    @Override
    public void initialize(AllowedOrderStatus allowedValue) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "AWAITING_MATERIAL".equals(value) ||
                "ADD_TO_STOCK".equals(value) ||
                "PRODUCTION".equals(value) ||
                "TESTING".equals(value) ||
                "SHIPPED".equals(value);
    }
}