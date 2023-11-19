package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.RawMaterialDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.models.Location;
import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.models.RawMaterial;
import com.fsd.inventopilot.repositories.LocationRepository;
import com.fsd.inventopilot.repositories.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import java.util.stream.Collectors;

@Component
public class RawMaterialDtoMapper {
    private final LocationRepository locationRepository;
    private final ProductRepository productRepository;

    public RawMaterialDtoMapper(
            LocationRepository locationRepository,
            ProductRepository productRepository) {
        this.locationRepository = locationRepository;
        this.productRepository = productRepository;
    }

    public RawMaterialDto mapToDto(RawMaterial rawMaterial) {
        RawMaterialDto dto = new RawMaterialDto();
        BeanUtils.copyProperties(rawMaterial, dto);

        dto.setLocationNames(rawMaterial.getLocations() != null
                ? rawMaterial.getLocations().stream().map(Location::getDepartment).collect(Collectors.toSet())
                : null);
        dto.setProductNames(rawMaterial.getProducts() != null
                ? rawMaterial.getProducts().stream().map(Product::getName).collect(Collectors.toSet())
                : null);

        return dto;
    }

    public RawMaterial mapToEntity(RawMaterialDto dto) {
        RawMaterial rawMaterial = new RawMaterial();
        BeanUtils.copyProperties(dto, rawMaterial);

        rawMaterial.setLocations(dto.getLocationNames() != null
                ? dto.getLocationNames().stream().map(department ->
                        locationRepository.findByDepartment(department)
                                .orElseThrow(() -> new RecordNotFoundException("Location not found with department: " + department)))
                .collect(Collectors.toSet())
                : null);
        rawMaterial.setProducts(dto.getProductNames() != null
                ? dto.getProductNames().stream().map(name ->
                        productRepository.findByName(name)
                                .orElseThrow(() -> new RecordNotFoundException("Product not found with name: " + name)))
                .collect(Collectors.toSet())
                : null);

        return rawMaterial;
    }
}