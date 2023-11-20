package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.models.ComponentType;
import com.fsd.inventopilot.validations.AllowedComponentType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedComponentTypeImpl implements ConstraintValidator<AllowedComponentType, Object> {

    @Override
    public void initialize(AllowedComponentType allowedComponentType) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are considered valid
        }

        if (value instanceof ComponentType componentType) { // Replace with the actual type used in your application
            // Replace with the actual type used in your application
            return ComponentType.SOLUTION.equals(componentType) ||
                    ComponentType.BOTTLE.equals(componentType) ||
                    ComponentType.CAP.equals(componentType) ||
                    ComponentType.INDUCTION_LINER.equals(componentType) ||
                    ComponentType.INNER_BAG.equals(componentType) ||
                    ComponentType.PRINTED_BAG.equals(componentType) ||
                    ComponentType.LABEL.equals(componentType);
        }

        return false; // non-enum values are considered invalid
    }
}