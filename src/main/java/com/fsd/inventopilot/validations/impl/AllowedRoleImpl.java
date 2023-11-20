package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.models.Role;
import com.fsd.inventopilot.validations.AllowedRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedRoleImpl implements ConstraintValidator<AllowedRole, Object> {

    @Override
    public void initialize(AllowedRole allowedRole) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are considered valid
        }

        if (value instanceof Role role) { // Replace with the actual type used in your application
            // Replace with the actual type used in your application
            return Role.USER.equals(role) ||
                    Role.SUPERUSER.equals(role) ||
                    Role.ADMIN.equals(role);
        }

        return false; // non-enum values are considered invalid
    }
}