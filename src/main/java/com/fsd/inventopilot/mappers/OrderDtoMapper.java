package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.OrderDto;
import com.fsd.inventopilot.dtos.OrderProductDto;
import com.fsd.inventopilot.helpers.FormatDate;
import com.fsd.inventopilot.models.Order;
import com.fsd.inventopilot.models.OrderProduct;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderDtoMapper {
    public OrderDto mapToDto(Order order) {
        OrderDto dto = new OrderDto();

        dto.setId(order.getId());
        if (order.getOrderProducts() != null) {
            Set<OrderProductDto> orderProductDtos = order.getOrderProducts()
                    .stream()
                    .map(OrderProductDtoMapper::mapToDto)
                    .collect(Collectors.toSet());
            dto.setOrderProducts(orderProductDtos);
        }

        dto.setOrderDate(FormatDate.formatTimestamp(order.getOrderDate()));

        return dto;
    }

    public Order mapToEntity(OrderDto dto) throws ParseException {
        Order order = new Order();

        order.setId(dto.getId());
        if (dto.getOrderProducts() != null) {
            Set<OrderProduct> orderProducts = dto.getOrderProducts()
                    .stream()
                    .map(OrderProductDtoMapper::mapToEntity)
                    .collect(Collectors.toSet());
            order.setOrderProducts(orderProducts);
        }

        // Using the FormatDate helper method to parse the date string
        order.setOrderDate(FormatDate.parseTimestamp(dto.getOrderDate()));

        return order;
    }
}
