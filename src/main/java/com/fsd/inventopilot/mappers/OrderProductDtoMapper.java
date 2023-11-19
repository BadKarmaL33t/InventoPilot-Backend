package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.OrderProductDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.models.Order;
import com.fsd.inventopilot.models.OrderProduct;
import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.repositories.OrderRepository;
import com.fsd.inventopilot.repositories.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderProductDtoMapper {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderProductDtoMapper(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderProductDto mapToDto(OrderProduct orderProduct) {
        OrderProductDto dto = new OrderProductDto();

        dto.setOrderId(orderProduct.getOrder().getId());
        dto.setProductName(orderProduct.getProduct().getName());
        dto.setQuantity(orderProduct.getQuantity());

        return dto;
    }

    public OrderProduct mapToEntity(OrderProductDto dto) {
        OrderProduct orderProduct = new OrderProduct();

        // Fetch Order entity based on orderId
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new RecordNotFoundException("Order not found with id: " + dto.getOrderId()));
        orderProduct.setOrder(order);

        // Fetch Product entity based on productName
        Product product = productRepository.findByName(dto.getProductName())
                .orElseThrow(() -> new RecordNotFoundException("Product not found with name: " + dto.getProductName()));
        orderProduct.setProduct(product);

        orderProduct.setQuantity(dto.getQuantity());

        return orderProduct;
    }
}
