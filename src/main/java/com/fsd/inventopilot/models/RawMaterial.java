package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "raw_materials")
public class RawMaterial {
    @Id
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private String batchNumber;
    @Column(nullable = false)
    private int minimalStock;
    @Column(nullable = false)
    private int maximalStock;
    @ManyToMany(mappedBy = "raw_materials")
    private Set<Location> locations;
    @OneToMany(mappedBy = "raw_materials")
    private Set<Product> products;
}
