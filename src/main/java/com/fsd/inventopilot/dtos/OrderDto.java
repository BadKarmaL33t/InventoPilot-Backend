package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.OrderStatus;
import com.fsd.inventopilot.validations.AllowedOrderStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Collection;

@Data
public class OrderDto {
    @Digits(integer = 10, fraction = 0)
    private Long id;
    private Collection<OrderProductDto> orderProducts;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4}) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
    private String orderDate;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4}) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")
    private String deliveryDate;
    @AllowedOrderStatus
    private OrderStatus orderStatus;
}
