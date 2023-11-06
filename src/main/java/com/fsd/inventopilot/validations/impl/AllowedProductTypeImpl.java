package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.validations.AllowedProductType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedProductTypeImpl implements ConstraintValidator<AllowedProductType, String> {
    @Override
    public void initialize(AllowedProductType allowedValue) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "RAW".equals(value) || "SOLUBLE".equals(value);
    }
}