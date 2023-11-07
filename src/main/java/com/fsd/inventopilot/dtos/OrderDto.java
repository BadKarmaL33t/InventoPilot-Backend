package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.Status;
import com.fsd.inventopilot.validations.AllowedStatus;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class OrderDto {
    @Pattern(regexp = "[0-9]+")
    private Long id;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<OrderProductDto> orderProducts;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4}) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
    private String orderDate;
    @AllowedStatus
    private Status status;
}
