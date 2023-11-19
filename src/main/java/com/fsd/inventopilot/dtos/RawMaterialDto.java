package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.Department;
import com.fsd.inventopilot.models.ProductStatus;
import com.fsd.inventopilot.validations.AllowedProductStatus;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Collection;

@Data
public class RawMaterialDto {
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    @Size(min = 2, max = 25)
    private String name;
    @Pattern(regexp = "[0-9]+")
    private int stock;
    @AllowedProductStatus
    private ProductStatus productStatus;
    @Pattern(regexp = "[0-9]+")
    private int used;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private String batchNumber;
    @Pattern(regexp = "[0-9]+")
    private int minimalStock;
    @Pattern(regexp = "[0-9]+")
    private int maximalStock;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Collection<Department> locationNames;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Collection<String> productNames;
}
