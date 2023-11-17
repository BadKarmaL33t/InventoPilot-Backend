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
    private ProductStatus productStatus;
    private int used;
    @Column(nullable = false)
    private String batchNumber;
    private int minimalStock;
    private int maximalStock;
    @ManyToMany(mappedBy = "raws")
    private Set<Location> locations;
    @OneToMany(mappedBy = "raw")
    private Set<Product> products;
}
