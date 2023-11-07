package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.Status;
import com.fsd.inventopilot.validations.AllowedStatus;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
@Transactional
public class OrderDto {
    @Pattern(regexp = "[0-9]+")
    private Long id;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private Set<OrderProductDto> orderProducts;
    @Temporal(TemporalType.TIMESTAMP)
    private String orderDate;
    @AllowedStatus
    private Status status;
}
