package com.fsd.inventopilot.services;

import com.fsd.inventopilot.dtos.OrderDto;
import com.fsd.inventopilot.dtos.OrderProductDto;

import java.text.ParseException;
import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();
    OrderDto getOrderDetails(Long id);
    OrderDto postOrder(OrderDto orderDto) throws ParseException;
    OrderDto updateOrder(Long id, OrderDto newOrder) throws ParseException;
    void deleteOrder(Long id);
    OrderDto updateOrderDetails(Long id, OrderDto updatedOrder);
    OrderDto addProductsToCart(Long id, List<OrderProductDto> orderProductDtos);
}