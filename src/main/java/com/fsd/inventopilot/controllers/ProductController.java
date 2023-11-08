package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.ProductDto;
import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.models.ProductComponent;
import com.fsd.inventopilot.models.ProductType;
import com.fsd.inventopilot.models.RawMaterial;
import com.fsd.inventopilot.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/app/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos;
        productDtos = productService.getAllProducts();

        return ResponseEntity.ok().body(productDtos);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<ProductDto>> getProductsByType(@PathVariable ProductType type) {
        List<ProductDto> productDtos = productService.getProductsByType(type);
        return ResponseEntity.ok().body(productDtos);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductDto> getProductDetails(@PathVariable("name") String name) {
        ProductDto dto = productService.getProductDetails(name);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> postProduct(@RequestBody ProductDto productDto) {
        ProductDto dto = productService.postProduct(productDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getName())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{name}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String name, @RequestBody ProductDto newProduct) {
        ProductDto dto = productService.updateProduct(name, newProduct);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String name) {
        productService.deleteProduct(name);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{name}")
    public ResponseEntity<ProductDto> updateProductDetails(@PathVariable String name, @RequestBody ProductDto updatedProduct) {
        ProductDto dto = productService.updateProductDetails(name, updatedProduct);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/{name}/raw")
    public ResponseEntity<ProductDto> addRawMaterialToProduct(
            @PathVariable("name") String productName,
            @RequestBody RawMaterial rawMaterial) {

        ProductDto dto = productService.addRawMaterialToProduct(productName, rawMaterial);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/{name}/components")
    public ResponseEntity<ProductDto> addProductComponentToProduct(
            @PathVariable("name") String productName,
            @RequestBody ProductComponent productComponent) {

        ProductDto dto = productService.addProductComponentToProduct(productName, productComponent);

        return ResponseEntity.ok().body(dto);
    }
}
