package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.ComponentType;
import com.fsd.inventopilot.models.ProductStatus;
import com.fsd.inventopilot.validations.AllowedComponentType;
import com.fsd.inventopilot.validations.AllowedProductStatus;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class ProductComponentDto {
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    @Size(min = 2, max = 25)
    private String name;
    @AllowedComponentType
    private ComponentType type;
    @Pattern(regexp = "[0-9]+")
    private int stock;
    @AllowedProductStatus
    private ProductStatus status;
    @Pattern(regexp = "[0-9]+")
    private int used;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private String serialNumber;
    @Pattern(regexp = "[0-9]+")
    private int minimalStock;
    @Pattern(regexp = "[0-9]+")
    private int maximalStock;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<LocationDto> locations;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<ProductDto> products;
}
