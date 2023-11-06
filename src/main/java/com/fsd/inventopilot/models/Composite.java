package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "composites")
public class Composite {
    @Id
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private float quantity;
    @Column(nullable = false)
    private String serialNumber;
    @Column(nullable = false)
    private int minimalStock;
    @Column(nullable = false)
    private int maximalStock;
    @ManyToMany(mappedBy = "composites")
    private Set<Location> locations;
    @ManyToMany
    @JoinTable(name = "composite_products",
            joinColumns = @JoinColumn(name = "composite_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private Set<Product> products;
}
