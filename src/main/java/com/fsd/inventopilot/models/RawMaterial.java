package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "rawMaterials")
public class RawMaterial {
    @Id
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private int stock;
    @Column
    private ProductStatus status;
    @Column
    private int used;
    @Column(nullable = false)
    private String batchNumber;
    @Column(nullable = false)
    private int minimalStock;
    @Column(nullable = false)
    private int maximalStock;
    @ManyToMany(mappedBy = "rawMaterials")
    private Set<Location> locations;
    @OneToMany(mappedBy = "rawMaterial")
    private Set<Product> products;
}
