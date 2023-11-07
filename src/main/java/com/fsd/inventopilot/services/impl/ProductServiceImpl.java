package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.ProductDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.mappers.ProductDtoMapper;
import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.repositories.ProductRepository;
import com.fsd.inventopilot.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto getProductDetails(String name) {
        Product existingProduct = productRepository.findByName(name);
        if (existingProduct != null) {
            return productDtoMapper.mapToDto(existingProduct);
        }
        throw new RecordNotFoundException("Product: " + name + " not found");
    }

    @Transactional
    public ProductDto postProduct(ProductDto productDto) {
        Product product = productDtoMapper.mapToEntity(productDto);
        productRepository.save(product);
        return productDtoMapper.mapToDto(product);
    }

    @Transactional
    public ProductDto updateProduct(String name, ProductDto newProduct) {
        Product existingProduct = productRepository.findByName(name);
        if (existingProduct != null) {
            Product updatedProduct = productDtoMapper.mapToEntity(newProduct);
            updatedProduct.setName(name);
            updatedProduct.setType(newProduct.getType());
            updatedProduct.setStock(newProduct.getStock());
            updatedProduct.setSold(newProduct.getSold());
            updatedProduct.setSerialNumber(newProduct.getSerialNumber());
            updatedProduct.setMinimalStock(newProduct.getMinimalStock());
            updatedProduct.setMaximalStock(newProduct.getMaximalStock());

            productRepository.save(updatedProduct);
            return productDtoMapper.mapToDto(updatedProduct);
        }
        throw new RecordNotFoundException("Product: " + name + " not found");
    }

    @Transactional
    public void deleteProduct(String name) {
        Product product = productRepository.findByName(name);
        if (product != null) {
            productRepository.delete(product);
        } else {
            throw new RecordNotFoundException("Product: " + name + " not found");
        }
    }

    @Transactional
    public ProductDto updateProductDetails(String name, ProductDto updatedProduct) {
        Product existingProduct = productRepository.findByName(name);
        if (existingProduct != null) {
            if (updatedProduct.getName() != null) {
                existingProduct.setName(updatedProduct.getName());
            }
            if (updatedProduct.getType() != null) {
                existingProduct.setType(updatedProduct.getType());
            }
            if (updatedProduct.getStock() != 0) {
                existingProduct.setStock(updatedProduct.getStock());
            }
            if (updatedProduct.getSold() != 0) {
                existingProduct.setSold(updatedProduct.getSold());
            }
            if (updatedProduct.getSerialNumber() != null) {
                existingProduct.setSerialNumber(updatedProduct.getSerialNumber());
            }
            if (updatedProduct.getMinimalStock() != 0) {
                existingProduct.setMinimalStock(updatedProduct.getMinimalStock());
            }
            if (updatedProduct.getMaximalStock() != 0) {
                existingProduct.setMaximalStock(updatedProduct.getMaximalStock());
            }

            productRepository.save(existingProduct);
            return productDtoMapper.mapToDto(existingProduct);
        }
        throw new RecordNotFoundException("Product: " + name + " not found");
    }
}
