package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.OrderProductDto;
import com.fsd.inventopilot.models.OrderProduct;
import org.springframework.stereotype.Component;

@Component
public class OrderProductDtoMapper {
    public static OrderProductDto mapToDto(OrderProduct orderProduct) {
        OrderProductDto dto = new OrderProductDto();

        dto.setId(orderProduct.getId());
        dto.setProduct(orderProduct.getProduct());
        dto.setQuantity(orderProduct.getQuantity());
        dto.setStatus(orderProduct.getStatus());

        return dto;
    }

    public static OrderProduct mapToEntity(OrderProductDto dto) {
        OrderProduct orderProduct = new OrderProduct();

        orderProduct.setId(dto.getId());
        orderProduct.setProduct(dto.getProduct());
        orderProduct.setQuantity(dto.getQuantity());
        orderProduct.setStatus(dto.getStatus());

        return orderProduct;
    }
}
