package com.fsd.inventopilot.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderProductTest {

    @Test
    public void testOrderProductCreation() {
        // Create an Order object for testing
        Order order = new Order();
        order.setId(1L);

        // Create a Product object for testing
        Product product = new Product();
        product.setName("TestProduct");

        // Create an OrderProduct object for testing
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setId(1L);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(5);

        // Validate the OrderProduct properties
        assertEquals(1L, orderProduct.getId());
        assertEquals(order, orderProduct.getOrder());
        assertEquals(product, orderProduct.getProduct());
        assertEquals(5, orderProduct.getQuantity());
    }
}