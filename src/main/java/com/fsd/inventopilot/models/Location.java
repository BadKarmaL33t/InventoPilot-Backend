package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "locations")
public class Location {
    @Id
    private Department department;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "locations_product_components",
            joinColumns = @JoinColumn(name = "locations_id"),
            inverseJoinColumns = @JoinColumn(name = "product_components_id"))
    private Set<ProductComponent> components;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "locations_raw_materials",
            joinColumns = @JoinColumn(name = "locations_id"),
            inverseJoinColumns = @JoinColumn(name = "raw_materials_id"))
    private Set<RawMaterial> raws;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "locations_products",
            joinColumns = @JoinColumn(name = "locations_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private Set<Product> products;
}