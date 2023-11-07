package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.OrderDto;
import com.fsd.inventopilot.dtos.OrderProductDto;
import com.fsd.inventopilot.models.Order;
import com.fsd.inventopilot.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/app/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orderDtos;
        orderDtos = orderService.getAllOrders();

        return ResponseEntity.ok().body(orderDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderDetails(@PathVariable("id") Long id) {
        OrderDto dto = orderService.getOrderDetails(id);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<OrderDto> postOrder(@RequestBody OrderDto orderDto) {
        OrderDto dto = orderService.postOrder(orderDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @PostMapping("/{id}/cart")
    public ResponseEntity<OrderDto> addProductsToCart(@PathVariable Long id, @RequestBody List<OrderProductDto> orderProductDtos) {
        OrderDto dto = orderService.addProductsToCart(id, orderProductDtos);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto newOrder) {
        OrderDto dto = orderService.updateOrder(id, newOrder);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrderDetails(@PathVariable Long id, @RequestBody OrderDto updatedOrder) {
        OrderDto dto = orderService.updateOrderDetails(id, updatedOrder);

        return ResponseEntity.ok().body(dto);
    }
}
