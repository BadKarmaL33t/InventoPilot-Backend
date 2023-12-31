package com.fsd.inventopilot.services;

import com.fsd.inventopilot.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductDetails(String name);
    ProductDto postProduct(ProductDto componentDto);
    ProductDto updateProduct(String name, ProductDto newProduct);
    void deleteProduct(String name);
    ProductDto updateProductDetails(String name, ProductDto updatedProduct);
    ProductDto addRawMaterialToProduct(String productName, String rawMaterialName);
    ProductDto removeRawMaterialFromProduct(String productName, String rawMaterialName);
    ProductDto addProductComponentToProduct(String productName, String productComponentName);
    ProductDto removeProductComponentFromProduct(String productName, String productComponentName);
}
