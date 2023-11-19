package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.LocationDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.models.Location;
import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.models.ProductComponent;
import com.fsd.inventopilot.models.RawMaterial;
import com.fsd.inventopilot.repositories.ProductComponentRepository;
import com.fsd.inventopilot.repositories.ProductRepository;
import com.fsd.inventopilot.repositories.RawMaterialRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LocationDtoMapper {
    private final ProductComponentRepository productComponentRepository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public LocationDtoMapper(
            ProductComponentRepository productComponentRepository,
            ProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository) {
        this.productComponentRepository = productComponentRepository;
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public LocationDto mapToDto(Location location) {
        LocationDto dto = new LocationDto();

        BeanUtils.copyProperties(location, dto);
        dto.setCompositeNames(location.getComponents().stream()
                .map(ProductComponent::getName)
                .collect(Collectors.toSet()));
        dto.setProductNames(location.getProducts().stream()
                .map(Product::getName)
                .collect(Collectors.toSet()));
        dto.setRawMaterialNames(location.getRaws().stream()
                .map(RawMaterial::getName)
                .collect(Collectors.toSet()));

        return dto;
    }

    public Location mapToEntity(LocationDto dto) {
        Location location = new Location();

        BeanUtils.copyProperties(dto, location);
        location.setComponents(dto.getCompositeNames().stream()
                .map(name -> productComponentRepository.findByName(name)
                        .orElseThrow(() -> new RecordNotFoundException("ProductComponent not found with name: " + name)))
                .collect(Collectors.toSet()));
        location.setProducts(dto.getProductNames().stream()
                .map(name -> productRepository.findByName(name)
                        .orElseThrow(() -> new RecordNotFoundException("Product not found with name: " + name)))
                .collect(Collectors.toSet()));
        location.setRaws(dto.getRawMaterialNames().stream()
                .map(name -> rawMaterialRepository.findByName(name)
                        .orElseThrow(() -> new RecordNotFoundException("RawMaterial not found with name: " + name)))
                .collect(Collectors.toSet()));

        return location;
    }
}