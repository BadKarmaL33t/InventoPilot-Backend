package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.Product;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderProductDto {
    @Pattern(regexp = "[0-9]+")
    private Long id;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Product product;
    @Pattern(regexp = "[0-9]+")
    private int quantity;
}
