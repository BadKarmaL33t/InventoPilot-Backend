package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.ComponentType;
import com.fsd.inventopilot.validations.AllowedComponentType;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Transactional
public class ComponentDto {
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    @Size(min = 2, max = 20)
    private String name;
    @AllowedComponentType
    private ComponentType type;
    @Pattern(regexp = "[0-9]+")
    private int quantity;
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
