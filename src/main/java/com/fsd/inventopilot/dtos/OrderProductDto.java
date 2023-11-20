package com.fsd.inventopilot.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderProductDto {
    @Digits(integer = 10, fraction = 0)
    private Long orderId;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private String productName;
    @Digits(integer = 10, fraction = 0)
    private int quantity;
}
