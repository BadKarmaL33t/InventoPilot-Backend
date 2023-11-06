package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


// TO-DO: allowed product type annotations


@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private ProductType type;
    @Column
    private int stock;
    @Column
    private int sold;
    @Column(nullable = false)
    private String serialNumber;
    @Column
    private int minimalStock;
    @Column
    private int maximalStock;
    @ManyToMany(mappedBy = "products")
    private Set<Location> locations;
    @ManyToOne
    @JoinColumn(name = "raw_materials_name")
    private RawMaterial rawMaterial;
    @ManyToMany(mappedBy = "products")
    private Set<Component> components;
}
