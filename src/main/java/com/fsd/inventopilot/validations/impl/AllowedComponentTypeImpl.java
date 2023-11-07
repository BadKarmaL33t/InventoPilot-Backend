package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.validations.AllowedComponentType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedComponentTypeImpl implements ConstraintValidator<AllowedComponentType, String> {
    @Override
    public void initialize(AllowedComponentType allowedValue) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "SOLUTION".equals(value) ||
                "BOTTLE".equals(value) ||
                "CAP".equals(value) ||
                "INDUCTION_LINER".equals(value) ||
                "INNER_BAG".equals(value) ||
                "PRINTED_BAG".equals(value) ||
                "LABEL".equals(value);
    }
}