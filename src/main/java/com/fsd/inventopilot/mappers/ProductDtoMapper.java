package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.ProductDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.models.*;
import com.fsd.inventopilot.repositories.LocationRepository;
import com.fsd.inventopilot.repositories.ProductComponentRepository;
import com.fsd.inventopilot.repositories.RawMaterialRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {
    private final LocationRepository locationRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductComponentRepository productComponentRepository;

    public ProductDtoMapper(
            LocationRepository locationRepository, RawMaterialRepository rawMaterialRepository,
            ProductComponentRepository productComponentRepository) {
        this.locationRepository = locationRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.productComponentRepository = productComponentRepository;
    }

    public ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();

        BeanUtils.copyProperties(product, dto);

        if (product.getLocations() != null) {
            dto.setLocationNames(product.getLocations().stream()
                    .map(Location::getDepartment)
                    .collect(Collectors.toSet()));
        } else {
            dto.setLocationNames(null);
        }
        dto.setRawMaterialName(product.getRaw() != null ? product.getRaw().getName() : null);

        if (product.getComponents() != null) {
            dto.setComponentNames(product.getComponents().stream()
                    .map(ProductComponent::getName)
                    .collect(Collectors.toSet()));
        } else {
            dto.setComponentNames(null);
        }
        return dto;
    }

    public Product mapToEntity(ProductDto dto) {
        Product product = new Product();

        BeanUtils.copyProperties(dto, product);

        if (dto.getLocationNames() != null) {
            product.setLocations(dto.getLocationNames().stream()
                    .map(departmentName -> locationRepository.findByDepartment(departmentName)
                            .orElseThrow(() -> new RecordNotFoundException("Location not found with department: " + departmentName)))
                    .collect(Collectors.toSet()));
        } else {
            product.setLocations(null);
        }

        if (dto.getRawMaterialName() != null) {
            product.setRaw(rawMaterialRepository.findByName(dto.getRawMaterialName())
                    .orElseThrow(() -> new RecordNotFoundException("RawMaterial not found with name: " + dto.getRawMaterialName())));
        } else {
            product.setRaw(null);
        }

        if (dto.getComponentNames() != null) {
            product.setComponents(dto.getComponentNames().stream()
                    .map(name -> productComponentRepository.findByName(name)
                            .orElseThrow(() -> new RecordNotFoundException("ProductComponent not found with name: " + name)))
                    .collect(Collectors.toSet()));
        } else {
            product.setComponents(null);
        }

        return product;
    }
}