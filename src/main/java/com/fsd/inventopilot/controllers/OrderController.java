package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.OrderDto;
import com.fsd.inventopilot.dtos.OrderProductDto;
import com.fsd.inventopilot.models.Order;
import com.fsd.inventopilot.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Optional<OrderDto>> getOrderDetails(@PathVariable("id") Long id) {
        Optional<OrderDto> dto = orderService.getOrderDetails(id);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) throws ParseException {
        OrderDto dto = orderService.createOrder(orderDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @PatchMapping("/{id}/add-items")
    public ResponseEntity<OrderDto> addProductToOrder(@PathVariable Long id, @RequestBody List<OrderProductDto> orderProductDtos) {
        OrderDto dto = orderService.addProductToOrder(id, orderProductDtos);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{id}/subtract-items")
    public ResponseEntity<OrderDto> subtractOrderProducts(@PathVariable Long id, @RequestBody List<OrderProductDto> orderProductDtos) {
        OrderDto dto = orderService.subtractOrderProducts(id, orderProductDtos);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}/items/{name}")
    public ResponseEntity<OrderDto> removeProductFromOrder(
            @PathVariable("id") Long orderId,
            @PathVariable("name") String productName) {

        OrderDto dto = orderService.removeProductFromOrder(orderId, productName);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto newOrder) throws ParseException {
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
