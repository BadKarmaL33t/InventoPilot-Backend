package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.ProductDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.mappers.ProductDtoMapper;
import com.fsd.inventopilot.models.*;
import com.fsd.inventopilot.repositories.LocationRepository;
import com.fsd.inventopilot.repositories.ProductComponentRepository;
import com.fsd.inventopilot.repositories.ProductRepository;
import com.fsd.inventopilot.repositories.RawMaterialRepository;
import com.fsd.inventopilot.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;
    private final LocationRepository locationRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductComponentRepository productComponentRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductDtoMapper productDtoMapper, LocationRepository locationRepository, RawMaterialServiceImpl rawMaterialService, ProductComponentServiceImpl productComponentService, RawMaterialRepository rawMaterialRepository, ProductComponentRepository productComponentRepository) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
        this.locationRepository = locationRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.productComponentRepository = productComponentRepository;
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
        Optional<Product> product = productRepository.findByName(name);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            return productDtoMapper.mapToDto(existingProduct);
        }
        throw new RecordNotFoundException("Product: " + name + " not found");
    }

    @Transactional
    public ProductDto postProduct(ProductDto productDto) {
        Product product = productDtoMapper.mapToEntity(productDto);

        Optional<Location> warehouse = locationRepository.findByDepartment(Department.WAREHOUSE);
        if (warehouse.isPresent()) {
            Location location = warehouse.get();
            location.getProducts().add(product);
            locationRepository.save(location);
        } else {
            throw new RecordNotFoundException("Location warehouse could not be found");
        }

        productRepository.save(product);
        return productDtoMapper.mapToDto(product);
    }

    @Transactional
    public ProductDto updateProduct(String name, ProductDto newProduct) {
        return productRepository.findByName(name)
                .map(existingProduct -> {
                    Product updatedProduct = productDtoMapper.mapToEntity(newProduct);
                    updatedProduct.setName(name);
                    productRepository.save(updatedProduct);
                    return productDtoMapper.mapToDto(updatedProduct);
                })
                .orElseThrow(() -> new RecordNotFoundException("Product: " + name + " not found"));
    }

    @Transactional
    public void deleteProduct(String name) {
        Optional<Product> product = productRepository.findByName(name);
        if (product.isPresent()) {
            Product foundProduct = product.get();
            productRepository.delete(foundProduct);
        } else {
            throw new RecordNotFoundException("Product: " + name + " not found");
        }
    }

    @Transactional
    public ProductDto updateProductDetails(String name, ProductDto updatedProduct) {
        Optional<Product> product = productRepository.findByName(name);
        if (product.isPresent()) {
            Product existingProduct = product.get();
            if (updatedProduct.getName() != null) {
                existingProduct.setName(updatedProduct.getName());
            }
            if (updatedProduct.getProductType() != null) {
                existingProduct.setProductType(updatedProduct.getProductType());
            }
            if (updatedProduct.getStock() != 0) {
                existingProduct.setStock(updatedProduct.getStock());
            }
            if (updatedProduct.getProductStatus() != null) {
                existingProduct.setProductStatus(updatedProduct.getProductStatus());
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
        } else {
            throw new RecordNotFoundException("Product: " + name + " not found");
        }
    }

    @Transactional
    public ProductDto addRawMaterialToProduct(String productName, String rawMaterialName) {
        Optional<Product> product = productRepository.findByName(productName);
        Optional<RawMaterial> rawMaterial = rawMaterialRepository.findByName(rawMaterialName);

        if (product.isPresent() && rawMaterial.isPresent()) {
            Product existingProduct = product.get();
            existingProduct.setRaw(rawMaterial.get());
            productRepository.save(existingProduct);
            return productDtoMapper.mapToDto(existingProduct);
        } else {
            throw new RecordNotFoundException("Product: " + productName + " or RawMaterial: " + rawMaterialName + " not found");
        }
    }

    @Transactional
    public ProductDto addProductComponentToProduct(String productName, String productComponentName) {
        Optional<Product> product = productRepository.findByName(productName);
        Optional<ProductComponent> productComponent = productComponentRepository.findByName(productComponentName);

        if (product.isPresent() && productComponent.isPresent()) {
            Product existingProduct = product.get();
            Set<ProductComponent> productComponents = existingProduct.getComponents();
            productComponents.add(productComponent.get());
            existingProduct.setComponents(productComponents);
            productRepository.save(existingProduct);
            return productDtoMapper.mapToDto(existingProduct);
        } else {
            throw new RecordNotFoundException("Product: " + productName + " or ProductComponent: " + productComponentName + " not found");
        }
    }
}
