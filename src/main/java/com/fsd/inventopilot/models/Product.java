package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private ProductType productType;
    private int stock;
    private ProductStatus productStatus;
    private int sold;
    @Column(nullable = false)
    private String serialNumber;
    private int minimalStock;
    private int maximalStock;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "products")
    private Set<Location> locations;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "raw_material_name")
    private RawMaterial raw;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "products", cascade = CascadeType.ALL)
    private Set<ProductComponent> components;
}
