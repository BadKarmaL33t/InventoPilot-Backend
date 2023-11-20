package com.fsd.inventopilot.validations.impl;

import com.fsd.inventopilot.models.Department;
import com.fsd.inventopilot.validations.AllowedDepartment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AllowedDepartmentImpl implements ConstraintValidator<AllowedDepartment, Object> {

    @Override
    public void initialize(AllowedDepartment allowedDepartment) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // null values are considered valid
        }

        if (value instanceof Department department) { // Replace with the actual type used in your application
            // Replace with the actual type used in your application
            return Department.WAREHOUSE.equals(department) ||
                    Department.MANUFACTURING.equals(department) ||
                    Department.LAB.equals(department);
        }

        return false; // non-enum values are considered invalid
    }
}