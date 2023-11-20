package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.models.OrderStatus; // Replace with the actual type used in your application
import com.fsd.inventopilot.validations.AllowedOrderStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedOrderStatusImpl implements ConstraintValidator<AllowedOrderStatus, Object> {

    @Override
    public void initialize(AllowedOrderStatus allowedOrderStatus) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are considered valid
        }

        if (value instanceof OrderStatus orderStatus) { // Replace with the actual type used in your application
            // Replace with the actual type used in your application
            return OrderStatus.AWAITING_MATERIAL.equals(orderStatus) ||
                    OrderStatus.ADD_TO_STOCK.equals(orderStatus) ||
                    OrderStatus.PRODUCTION.equals(orderStatus) ||
                    OrderStatus.TESTING.equals(orderStatus) ||
                    OrderStatus.SHIPPED.equals(orderStatus);
        }

        return false; // non-enum values are considered invalid
    }
}