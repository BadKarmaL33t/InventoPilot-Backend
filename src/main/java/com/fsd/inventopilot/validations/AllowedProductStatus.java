package com.fsd.inventopilot.validations;

import com.fsd.inventopilot.validations.impl.AllowedProductStatusImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedProductStatusImpl.class)
@ReportAsSingleViolation
public @interface AllowedProductStatus {
    String message() default "Invalid value. Accepted values are IN_STOCK, OUT_OF_STOCK, AWAITING_BACKORDER";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}