package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "components")
public class Component {
    @Id
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private ComponentType type;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private String serialNumber;
    @Column(nullable = false)
    private int minimalStock;
    @Column(nullable = false)
    private int maximalStock;
    @ManyToMany(mappedBy = "components")
    private Set<Location> locations;
    @ManyToMany
    @JoinTable(name = "components_products",
            joinColumns = @JoinColumn(name = "components_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private Set<Product> products;
}
