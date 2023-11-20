package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.models.ProductType;
import com.fsd.inventopilot.validations.AllowedProductType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedProductTypeImpl implements ConstraintValidator<AllowedProductType, Object> {

    @Override
    public void initialize(AllowedProductType allowedProductType) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are considered valid
        }

        if (value instanceof ProductType productType) { // Replace with the actual type used in your application
            // Replace with the actual type used in your application
            return ProductType.RAW.equals(productType) ||
                    ProductType.SOLUBLE.equals(productType);
        }

        return false; // non-enum values are considered invalid
    }
}