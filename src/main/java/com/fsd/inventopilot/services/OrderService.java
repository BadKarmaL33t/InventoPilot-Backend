package com.fsd.inventopilot.services;

import com.fsd.inventopilot.dtos.OrderDto;
import com.fsd.inventopilot.dtos.OrderProductDto;
import com.fsd.inventopilot.models.OrderStatus;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> getAllOrders();
    Optional<OrderDto> getOrderDetails(Long id);
    OrderDto createOrder(OrderDto orderDto) throws ParseException;
    OrderDto updateOrder(Long id, OrderDto newOrder) throws ParseException;
    void deleteOrder(Long id);
    OrderDto updateOrderDetails(Long id, OrderDto updatedOrder);
    OrderDto addProductToOrder(Long id, List<OrderProductDto> orderProductDtos);
    OrderDto updateOrderStatus(Long orderId, OrderStatus newStatus);
    OrderDto removeProductFromOrder(Long orderId, String productName);
    OrderDto subtractOrderProducts(Long orderId, List<OrderProductDto> orderProductDtos);
}
