package com.fsd.inventopilot.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderProductDto {
    @Pattern(regexp = "[0-9]+")
    private Long orderId;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private String productName;
    @Pattern(regexp = "[0-9]+")
    private int quantity;
}
