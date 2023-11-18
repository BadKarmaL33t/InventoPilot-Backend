package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.ProductComponentDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.models.Location;
import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.models.ProductComponent;
import com.fsd.inventopilot.repositories.LocationRepository;
import com.fsd.inventopilot.repositories.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Component
public class ProductComponentDtoMapper {
    private final LocationRepository locationRepository;
    private final ProductRepository productRepository;

    public ProductComponentDtoMapper(
            LocationRepository locationRepository,
            ProductRepository productRepository) {
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
    }

    public ProductComponentDto mapToDto(ProductComponent component) {
        ProductComponentDto dto = new ProductComponentDto();

        BeanUtils.copyProperties(component, dto);
        dto.setLocationNames(component.getLocations().stream()
                .map(Location::getDepartment)
                .collect(Collectors.toSet()));
        dto.setProductNames(component.getProducts().stream()
                .map(Product::getName)
                .collect(Collectors.toSet()));

        return dto;
    }

    public ProductComponent mapToEntity(ProductComponentDto dto) {
        ProductComponent component = new ProductComponent();

        BeanUtils.copyProperties(dto, component);
        component.setLocations(dto.getLocationNames().stream()
                .map(department -> locationRepository.findByDepartment(department)
                        .orElseThrow(() -> new RecordNotFoundException("Location not found with department: " + department)))
                .collect(Collectors.toSet()));

        component.setProducts(dto.getProductNames().stream()
                .map(name -> productRepository.findByName(name)
                        .orElseThrow(() -> new RecordNotFoundException("Product not found with name: " + name)))
                .collect(Collectors.toSet()));

        return component;
    }
}