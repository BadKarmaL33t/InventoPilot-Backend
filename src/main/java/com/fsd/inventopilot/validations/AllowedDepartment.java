package com.fsd.inventopilot.validations;

import com.fsd.inventopilot.validations.impl.AllowedRoleImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// custom annotation (custom annotations must be declared as public interfaces
@Target({ElementType.FIELD}) // meta-annotation must be applied to class fields (variables)
@Retention(RetentionPolicy.RUNTIME) // means the annotation is available at runtime and can be accessed via reflection
@Constraint(validatedBy = AllowedRoleImpl.class) //  specifies the class that implements the actual validation logic for the custom constraint
@ReportAsSingleViolation // indicates that a single violation message should be reported for the constraint, even if multiple constraints are violated
public @interface AllowedDepartment {
    String message() default "Invalid value. Accepted values are WAREHOUSE, MANUFACTURING or LAB";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
