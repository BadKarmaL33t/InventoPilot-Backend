package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> orderProducts;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    private Date deliveryDate;
    private OrderStatus orderStatus;
}
