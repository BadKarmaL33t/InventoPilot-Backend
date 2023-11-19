package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.OrderDto;
import com.fsd.inventopilot.dtos.OrderProductDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.mappers.OrderDtoMapper;
import com.fsd.inventopilot.models.*;
import com.fsd.inventopilot.repositories.OrderRepository;
import com.fsd.inventopilot.repositories.ProductRepository;
import com.fsd.inventopilot.services.OrderService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
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
                .map(order -> {
                    // Initialize the orderProducts collection to trigger eager fetching
                    Hibernate.initialize(order.getOrderProducts());
                    return orderDtoMapper.mapToDto(order);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<OrderDto> getOrderDetails(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.map(order -> {
            // Initialize the orderProducts collection to trigger eager fetching
            Hibernate.initialize(order.getOrderProducts());
            return orderDtoMapper.mapToDto(order);
        });
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) throws ParseException {
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

        if (updatedOrder.getOrderStatus() != null) {
            existingOrder.setOrderStatus(updatedOrder.getOrderStatus());
        }

        Order savedOrder = orderRepository.save(existingOrder);
        return orderDtoMapper.mapToDto(savedOrder);
    }

    @Transactional
    public OrderDto addProductToOrder(Long orderId, List<OrderProductDto> orderProductDtos) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        Order order = optionalOrder.orElseThrow(() ->
                new RecordNotFoundException("Order not found: " + orderId));

        for (OrderProductDto orderProductDto : orderProductDtos) {
            Optional<Product> optionalProduct = productRepository.findByName(orderProductDto.getProductName());

            Product product = optionalProduct.orElseThrow(() ->
                    new RecordNotFoundException("Product not found: " + orderProductDto.getProductName()));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(orderProductDto.getQuantity());

            order.getOrderProducts().add(orderProduct);
        }
        orderRepository.save(order);

        return orderDtoMapper.mapToDto(order);
    }

    @Transactional
    public OrderDto updateOrderProducts(Long orderId, String productName, List<OrderProductDto> orderProductDtos) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        Order order = optionalOrder.orElseThrow(() ->
                new RecordNotFoundException("Order not found: " + orderId));

        for (OrderProductDto orderProductDto : orderProductDtos) {
            Optional<OrderProduct> optionalOrderProduct = order.getOrderProducts().stream()
                    .filter(op -> op.getProduct().getName().equals(orderProductDto.getProductName()))
                    .findFirst();

            optionalOrderProduct.ifPresent(orderProduct -> orderProduct.setQuantity(orderProductDto.getQuantity()));

            if (optionalOrderProduct.isEmpty()) {
                Optional<Product> optionalProduct = productRepository.findByName(orderProductDto.getProductName());
                Product product = optionalProduct.orElseThrow(() ->
                        new RecordNotFoundException("Product not found: " + orderProductDto.getProductName()));

                OrderProduct newOrderProduct = new OrderProduct();
                newOrderProduct.setOrder(order);
                newOrderProduct.setProduct(product);
                newOrderProduct.setQuantity(orderProductDto.getQuantity());

                order.getOrderProducts().add(newOrderProduct);
            }
        }

        // Remove products if with the update no orders are associated with them
        order.getOrderProducts().removeIf(orderProduct -> orderProductDtos.stream()
                .noneMatch(dto -> dto.getProductName().equals(orderProduct.getProduct().getName())));

        orderRepository.save(order);

        return orderDtoMapper.mapToDto(order);
    }

    @Transactional
    public OrderDto removeProductFromOrder(Long orderId, String productName) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        Order order = optionalOrder.orElseThrow(() ->
                new RecordNotFoundException("Order not found: " + orderId));

        Optional<OrderProduct> optionalOrderProduct = order.getOrderProducts().stream()
                .filter(op -> op.getProduct().getName().equals(productName))
                .findFirst();

        OrderProduct orderProduct = optionalOrderProduct.orElseThrow(() ->
                new RecordNotFoundException("Product not found in order: " + productName));

        order.getOrderProducts().remove(orderProduct);
        orderRepository.save(order);

        return orderDtoMapper.mapToDto(order);
    }

    @Transactional
    public OrderDto updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RecordNotFoundException("Order not found: " + orderId));

        if (newStatus == OrderStatus.AWAITING_MATERIAL) {
            order.setOrderStatus(newStatus);
        } else if (newStatus == OrderStatus.ADD_TO_STOCK) {
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                Product product = orderProduct.getProduct();
                Location warehouse = product.getLocations().stream()
                        .filter(location -> location.getDepartment() == Department.WAREHOUSE)
                        .findFirst()
                        .orElseThrow(() -> new RecordNotFoundException("Warehouse location not found"));

                // Check if the product is already in the warehouse
                Product existingProduct = warehouse.getProducts().stream()
                        .filter(p -> p.getName().equals(product.getName()))
                        .findFirst()
                        .orElse(null);

                if (existingProduct != null) {
                    // Update the quantity in the warehouse
                    existingProduct.setStock(existingProduct.getStock() + orderProduct.getQuantity());
                } else {
                    // Add the product to the warehouse
                    product.setStock(orderProduct.getQuantity());
                    warehouse.getProducts().add(product);
                }
            }
            order.setOrderStatus(newStatus);
        } else if (newStatus == OrderStatus.PRODUCTION) {
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                Product product = orderProduct.getProduct();
                Location manufacturing = product.getLocations().stream()
                        .filter(location -> location.getDepartment() == Department.MANUFACTURING)
                        .findFirst()
                        .orElseThrow(() -> new RecordNotFoundException("Manufacturing location not found"));

                // Check if raw material stock will drop below zero
                RawMaterial rawMaterial = product.getRaw();
                int quantity = orderProduct.getQuantity();
                if (rawMaterial.getStock() - quantity < 0) {
                    throw new RuntimeException("Raw material stock can't be less than zero");
                }

                // Subtract the raw material from stock
                rawMaterial.setStock(rawMaterial.getStock() - quantity);
                manufacturing.getRaws().add(rawMaterial);

                for (ProductComponent productComponent : product.getComponents()) {
                    // Check if product component stock will drop below zero
                    quantity = orderProduct.getQuantity();
                    if (productComponent.getStock() - quantity < 0) {
                        throw new RuntimeException("Product component stock can't be less than zero");
                    }

                    // Subtract product components from stock
                    productComponent.setStock(productComponent.getStock() - quantity);
                    manufacturing.getComponents().add(productComponent);
                }
            }
            order.setOrderStatus(newStatus);
        } else if (newStatus == OrderStatus.TESTING) {
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                Product product = orderProduct.getProduct();
                Location lab = product.getLocations().stream()
                        .filter(location -> location.getDepartment() == Department.LAB)
                        .findFirst()
                        .orElseThrow(() -> new RecordNotFoundException("Lab location not found"));

                Location manufacturing = product.getLocations().stream()
                        .filter(location -> location.getDepartment() == Department.MANUFACTURING)
                        .findFirst()
                        .orElseThrow(() -> new RecordNotFoundException("Manufacturing location not found"));

                RawMaterial rawMaterial = product.getRaw();
                int quantity = orderProduct.getQuantity();
                rawMaterial.setStock(rawMaterial.getStock() - quantity);
                lab.getRaws().add(rawMaterial);

                for (ProductComponent productComponent : manufacturing.getComponents()) {
                    quantity = orderProduct.getQuantity();
                    productComponent.setStock(productComponent.getStock() - quantity);
                    lab.getComponents().add(productComponent);
                }

                Location warehouse = product.getLocations().stream()
                        .filter(location -> location.getDepartment() == Department.WAREHOUSE)
                        .findFirst()
                        .orElseThrow(() -> new RecordNotFoundException("Warehouse location not found"));

                quantity = orderProduct.getQuantity();
                // Update the quantity in the warehouse
                Product existingProduct = warehouse.getProducts().stream()
                        .filter(p -> p.getName().equals(product.getName()))
                        .findFirst()
                        .orElse(null);

                if (existingProduct != null) {
                    existingProduct.setStock(existingProduct.getStock() - quantity);
                }
            }
            order.setOrderStatus(newStatus);
        } else if (newStatus == OrderStatus.SHIPPED) {
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                Product product = orderProduct.getProduct();
                Location warehouse = product.getLocations().stream()
                        .filter(location -> location.getDepartment() == Department.WAREHOUSE)
                        .findFirst()
                        .orElseThrow(() -> new RecordNotFoundException("Warehouse location not found"));

                int quantity = orderProduct.getQuantity();
                // Update the quantity in the warehouse
                warehouse.getProducts().stream()
                        .filter(p -> p.getName().equals(product.getName()))
                        .findFirst().ifPresent(existingProduct -> existingProduct.setStock(existingProduct.getStock() - quantity));

            }
            order.setOrderStatus(newStatus);
        }
        orderRepository.save(order);
        return null;
    }
}