package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.Department;
import com.fsd.inventopilot.validations.AllowedDepartment;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Transactional
public class LocationDto {
    @AllowedDepartment
    private Department department;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<ProductComponentDto> composites;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<RawMaterialDto> rawMaterials;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<ProductDto> products;
}
