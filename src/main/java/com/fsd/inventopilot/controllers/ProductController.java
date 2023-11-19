package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.ProductDto;
import com.fsd.inventopilot.models.Product;
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

    @PatchMapping("/{name}/raw/{rawMaterialName}")
    public ResponseEntity<ProductDto> addRawMaterialToProduct(
            @PathVariable("name") String productName,
            @PathVariable("rawMaterialName") String rawMaterialName) {

        ProductDto dto = productService.addRawMaterialToProduct(productName, rawMaterialName);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{name}/raw/{rawMaterialName}")
    public ResponseEntity<ProductDto> removeRawMaterialFromProduct(
            @PathVariable("name") String productName,
            @PathVariable("rawMaterialName") String rawMaterialName) {

        ProductDto dto = productService.removeRawMaterialFromProduct(productName, rawMaterialName);

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/{name}/components/{productComponentName}")
    public ResponseEntity<ProductDto> addProductComponentToProduct(
            @PathVariable("name") String productName,
            @PathVariable("productComponentName") String productComponentName) {

        ProductDto dto = productService.addProductComponentToProduct(productName, productComponentName);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{name}/components/{productComponentName}")
    public ResponseEntity<ProductDto> removeProductComponentFromProduct(
            @PathVariable("name") String productName,
            @PathVariable("productComponentName") String productComponentName) {

        ProductDto dto = productService.removeProductComponentFromProduct(productName, productComponentName);

        return ResponseEntity.ok().body(dto);
    }
}
