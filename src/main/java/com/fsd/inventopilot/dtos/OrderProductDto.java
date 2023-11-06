package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.models.Status;
import com.fsd.inventopilot.validations.AllowedStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Transactional
public class OrderProductDto {
    @Pattern(regexp = "[0-9]+")
    private Long id;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Product product;
    @Pattern(regexp = "[0-9]+")
    private int quantity;
    @AllowedStatus
    private Status status;
}
