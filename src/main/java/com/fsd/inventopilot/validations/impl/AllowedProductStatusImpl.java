package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.models.ProductStatus;
import com.fsd.inventopilot.validations.AllowedProductStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedProductStatusImpl implements ConstraintValidator<AllowedProductStatus, Object> {

    @Override
    public void initialize(AllowedProductStatus allowedValue) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are considered valid
        }

        if (value instanceof ProductStatus productStatus) {
            return ProductStatus.IN_STOCK.equals(productStatus) ||
                    ProductStatus.OUT_OF_STOCK.equals(productStatus) ||
                    ProductStatus.AWAITING_BACKORDER.equals(productStatus);
        }

        return false; // non-enum values are considered invalid
    }
}