package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


// TO-DO: allowed department and allowed process annotations


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
    @JoinTable(name = "location_raw_material",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "raw_material_id"))
    private Set<RawMaterial> rawMaterials;
    @ManyToMany
    @JoinTable(name = "location_composites",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "composites_id"))
    private Set<Composite> composites;
    @ManyToMany
    @JoinTable(name = "location_products",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private Set<Product> products;
}
