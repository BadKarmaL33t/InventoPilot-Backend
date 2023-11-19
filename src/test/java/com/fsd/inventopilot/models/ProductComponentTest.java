package com.fsd.inventopilot.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ProductComponentTest {

    @InjectMocks
    private ProductComponent productComponent;

    @Test
    public void testGettersAndSetters() {
        // Set values using setters
        productComponent.setName("ComponentName");
        productComponent.setComponentType(ComponentType.BOTTLE);
        productComponent.setStock(50);
        productComponent.setProductStatus(ProductStatus.IN_STOCK);
        productComponent.setUsed(10);
        productComponent.setSerialNumber("Serial123");
        productComponent.setMinimalStock(20);
        productComponent.setMaximalStock(100);

        // Create mock objects for relationships
        Location location1 = mock(Location.class);
        Location location2 = mock(Location.class);
        Collection<Location> locations = new ArrayList<>();
        locations.add(location1);
        locations.add(location2);

        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        Collection<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        // Set relationships
        productComponent.setLocations(locations);
        productComponent.setProducts(products);

        // Test getters
        assertEquals("ComponentName", productComponent.getName());
        assertEquals(ComponentType.BOTTLE, productComponent.getComponentType());
        assertEquals(50, productComponent.getStock());
        assertEquals(ProductStatus.IN_STOCK, productComponent.getProductStatus());
        assertEquals(10, productComponent.getUsed());
        assertEquals("Serial123", productComponent.getSerialNumber());
        assertEquals(20, productComponent.getMinimalStock());
        assertEquals(100, productComponent.getMaximalStock());

        // Test relationships
        assertEquals(locations, productComponent.getLocations());
        assertEquals(products, productComponent.getProducts());
    }
}