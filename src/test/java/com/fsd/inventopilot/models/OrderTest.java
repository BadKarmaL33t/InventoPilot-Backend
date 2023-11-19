package com.fsd.inventopilot.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @InjectMocks
    private Order order;

    @Test
    public void testGettersAndSetters() {
        // Set values using setters
        order.setId(1L);

        OrderProduct orderProduct1 = mock(OrderProduct.class);
        OrderProduct orderProduct2 = mock(OrderProduct.class);
        Collection<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(orderProduct1);
        orderProducts.add(orderProduct2);

        order.setOrderProducts(orderProducts);
        order.setOrderDate(new Date());
        order.setDeliveryDate(new Date());
        order.setOrderStatus(OrderStatus.PENDING);

        // Test getters
        assertEquals(1L, order.getId());
        assertEquals(orderProducts, order.getOrderProducts());
        assertNotNull(order.getOrderDate());
        assertNotNull(order.getDeliveryDate());
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
    }

}
