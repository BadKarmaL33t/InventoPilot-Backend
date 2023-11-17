package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "product_components")
public class ProductComponent {
    @Id
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private ComponentType componentType;
    private int stock;
    @Column(nullable = false)
    private ProductStatus productStatus;
    private int used;
    @Column(nullable = false)
    private String serialNumber;
    private int minimalStock;
    private int maximalStock;
    @ManyToMany(mappedBy = "components")
    private Set<Location> locations;
    @ManyToMany
    @JoinTable(name = "product_components_products",
            joinColumns = @JoinColumn(name = "product_components_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private Set<Product> products;
}
