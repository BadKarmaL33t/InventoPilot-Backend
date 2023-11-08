package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.ProductStatus;
import com.fsd.inventopilot.models.ProductType;
import com.fsd.inventopilot.validations.AllowedProductStatus;
import com.fsd.inventopilot.validations.AllowedProductType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class ProductDto {
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    @Size(min = 2, max = 25)
    private String name;
    @AllowedProductType
    private ProductType type;
    @Pattern(regexp = "[0-9]+")
    private int stock;
    @AllowedProductStatus
    private ProductStatus status;
    @Pattern(regexp = "[0-9]+")
    private int sold;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private String serialNumber;
    @Pattern(regexp = "[0-9]+")
    private int minimalStock;
    @Pattern(regexp = "[0-9]+")
    private int maximalStock;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<LocationDto> locations;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private RawMaterialDto rawMaterial;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<ProductComponentDto> components;
}
