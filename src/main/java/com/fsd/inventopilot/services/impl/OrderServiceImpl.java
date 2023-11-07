package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.OrderDto;
import com.fsd.inventopilot.dtos.OrderProductDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.mappers.OrderDtoMapper;
import com.fsd.inventopilot.models.Order;
import com.fsd.inventopilot.models.OrderProduct;
import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.repositories.OrderRepository;
import com.fsd.inventopilot.repositories.ProductRepository;
import com.fsd.inventopilot.services.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDtoMapper orderDtoMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, OrderDtoMapper orderDtoMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderDtoMapper = orderDtoMapper;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderDetails(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order not found: " + id));
        return orderDtoMapper.mapToDto(order);
    }

    @Transactional
    public OrderDto postOrder(OrderDto orderDto) throws ParseException {
        Order order = orderDtoMapper.mapToEntity(orderDto);
        orderRepository.save(order);
        return orderDtoMapper.mapToDto(order);
    }

    @Transactional
    public OrderDto updateOrder(Long id, OrderDto newOrder) throws ParseException {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order not found: " + id));

        Order updatedOrder = orderDtoMapper.mapToEntity(newOrder);
        updatedOrder.setId(existingOrder.getId());
        updatedOrder.setOrderDate(existingOrder.getOrderDate());
        updatedOrder.setOrderProducts(existingOrder.getOrderProducts());

        Order savedOrder = orderRepository.save(updatedOrder);
        return orderDtoMapper.mapToDto(savedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order not found: " + id));
        orderRepository.delete(existingOrder);
    }

    @Transactional
    public OrderDto updateOrderDetails(Long id, OrderDto updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order not found: " + id));

        if (updatedOrder.getStatus() != null) {
            existingOrder.setStatus(updatedOrder.getStatus());
        }

        Order savedOrder = orderRepository.save(existingOrder);
        return orderDtoMapper.mapToDto(savedOrder);
    }

    @Transactional
    public OrderDto addProductsToCart(Long orderId, List<OrderProductDto> orderProductDtos) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RecordNotFoundException("Order not found: " + orderId));

        for (OrderProductDto orderProductDto : orderProductDtos) {
            Product product = productRepository.findById(orderProductDto.getProduct().getName())
                    .orElseThrow(() -> new RecordNotFoundException("Product not found: " + orderProductDto.getProduct().getName()));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(orderProductDto.getQuantity());

            order.getOrderProducts().add(orderProduct);
        }
        orderRepository.save(order);

        return orderDtoMapper.mapToDto(order);
    }
}