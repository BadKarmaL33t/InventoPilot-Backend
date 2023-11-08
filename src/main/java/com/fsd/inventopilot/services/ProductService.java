package com.fsd.inventopilot.services;

import com.fsd.inventopilot.dtos.ProductDto;
import com.fsd.inventopilot.models.ProductComponent;
import com.fsd.inventopilot.models.ProductType;
import com.fsd.inventopilot.models.RawMaterial;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByType(ProductType type);
    ProductDto getProductDetails(String name);
    ProductDto postProduct(ProductDto componentDto);
    ProductDto updateProduct(String name, ProductDto newProduct);
    void deleteProduct(String name);
    ProductDto updateProductDetails(String name, ProductDto updatedProduct);
    ProductDto addRawMaterialToProduct(String productName, RawMaterial rawMaterial);
    ProductDto addProductComponentToProduct(String productName, ProductComponent productComponent);
}
