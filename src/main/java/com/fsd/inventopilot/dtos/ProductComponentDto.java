package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.ComponentType;
import com.fsd.inventopilot.models.Department;
import com.fsd.inventopilot.models.ProductStatus;
import com.fsd.inventopilot.validations.AllowedComponentType;
import com.fsd.inventopilot.validations.AllowedProductStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Collection;

@Data
public class ProductComponentDto {
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    @Size(min = 2, max = 25)
    private String name;
    @AllowedComponentType
    private ComponentType componentType;
    @Digits(integer = 10, fraction = 0)
    private int stock;
    @AllowedProductStatus
    private ProductStatus productStatus;
    @Digits(integer = 10, fraction = 0)
    private int used;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private String serialNumber;
    @Digits(integer = 10, fraction = 0)
    private int minimalStock;
    @Digits(integer = 10, fraction = 0)
    private int maximalStock;
    private Collection<Department> locationNames;
    private Collection<String> productNames;
}
