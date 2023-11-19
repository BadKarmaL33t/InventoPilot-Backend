import com.fsd.inventopilot.models.Location;
import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.models.ProductStatus;
import com.fsd.inventopilot.models.RawMaterial;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RawMaterialTest {

    @InjectMocks
    private RawMaterial rawMaterial;

    @Test
    public void testGettersAndSetters() {
        // Set values using setters
        rawMaterial.setName("MaterialName");
        rawMaterial.setStock(50);
        rawMaterial.setProductStatus(ProductStatus.IN_STOCK);
        rawMaterial.setUsed(10);
        rawMaterial.setBatchNumber("Batch123");
        rawMaterial.setMinimalStock(20);
        rawMaterial.setMaximalStock(100);

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
        rawMaterial.setLocations(locations);
        rawMaterial.setProducts(products);

        // Test getters
        assertEquals("MaterialName", rawMaterial.getName());
        assertEquals(50, rawMaterial.getStock());
        assertEquals(ProductStatus.IN_STOCK, rawMaterial.getProductStatus());
        assertEquals(10, rawMaterial.getUsed());
        assertEquals("Batch123", rawMaterial.getBatchNumber());
        assertEquals(20, rawMaterial.getMinimalStock());
        assertEquals(100, rawMaterial.getMaximalStock());

        // Test relationships
        assertEquals(locations, rawMaterial.getLocations());
        assertEquals(products, rawMaterial.getProducts());
    }
}
