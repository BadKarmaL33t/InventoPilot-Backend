package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.OrderProductDto;
import com.fsd.inventopilot.models.OrderProduct;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

@Component
public class OrderProductDtoMapper {
    public OrderProductDto mapToDto(OrderProduct orderProduct) {
        OrderProductDto dto = new OrderProductDto();

        BeanUtils.copyProperties(orderProduct, dto);

        return dto;
    }

    public OrderProduct mapToEntity(OrderProductDto dto) {
        OrderProduct orderProduct = new OrderProduct();

        BeanUtils.copyProperties(dto, orderProduct);

        return orderProduct;
    }
}
