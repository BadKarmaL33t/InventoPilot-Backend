package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @Column(nullable = false, unique = true)
    private Department department;
    @Column
    private String location;
    @ManyToMany
    @JoinTable(name = "locationscomposites",
            joinColumns = @JoinColumn(name = "locations_id"),
            inverseJoinColumns = @JoinColumn(name = "composites_id"))
    private Set<ProductComponent> composites;
    @ManyToMany
    @JoinTable(name = "locations_products",
            joinColumns = @JoinColumn(name = "locations_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private Set<Product> products;
}
