package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.Department;
import com.fsd.inventopilot.validations.AllowedDepartment;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class LocationDto {
    @AllowedDepartment
    private Department department;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<String> compositeNames;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<String> rawMaterialNames;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<String> productNames;
}