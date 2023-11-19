package com.fsd.inventopilot.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    @Test
    public void testLocationCreation() {
        // Create ProductComponent, RawMaterial, and Product objects for testing
        ProductComponent productComponent = new ProductComponent();
        productComponent.setName("TestComponent");

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName("TestMaterial");

        Product product = new Product();
        product.setName("TestProduct");

        // Create a Location object for testing
        Location location = new Location();
        location.setDepartment(Department.WAREHOUSE);
        location.setComponents(new HashSet<>(Arrays.asList(productComponent)));
        location.setRaws(new HashSet<>(Arrays.asList(rawMaterial)));
        location.setProducts(new HashSet<>(Arrays.asList(product)));

        // Validate the Location properties
        assertEquals(Department.WAREHOUSE, location.getDepartment());
        assertTrue(location.getComponents().contains(productComponent));
        assertTrue(location.getRaws().contains(rawMaterial));
        assertTrue(location.getProducts().contains(product));
    }
}
