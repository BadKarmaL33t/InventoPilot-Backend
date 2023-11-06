package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.RawMaterialDto;
import com.fsd.inventopilot.models.RawMaterial;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RawMaterialDtoMapper {
    public static RawMaterialDto mapToDto(RawMaterial rawMaterial) {
        RawMaterialDto dto = new RawMaterialDto();

        dto.setName(rawMaterial.getName());
        dto.setQuantity(rawMaterial.getQuantity());
        dto.setBatchNumber(rawMaterial.getBatchNumber());
        dto.setMinimalStock(rawMaterial.getMinimalStock());
        dto.setMaximalStock(rawMaterial.getMaximalStock());
        dto.setLocations(rawMaterial.getLocations().stream()
                .map(LocationDtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setProducts(rawMaterial.getProducts().stream()
                .map(ProductDtoMapper::mapToDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    public static RawMaterial mapToEntity(RawMaterialDto dto) {
        RawMaterial rawMaterial = new RawMaterial();

        rawMaterial.setName(dto.getName());
        rawMaterial.setQuantity(dto.getQuantity());
        rawMaterial.setBatchNumber(dto.getBatchNumber());
        rawMaterial.setMinimalStock(dto.getMinimalStock());
        rawMaterial.setMaximalStock(dto.getMaximalStock());
        rawMaterial.setLocations(dto.getLocations().stream()
                .map(LocationDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        rawMaterial.setProducts(dto.getProducts().stream()
                .map(ProductDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));

        return rawMaterial;
    }
}
