package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @OneToMany(mappedBy = "order")
    @Column
    private Set<OrderProduct> orderProducts;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
}
