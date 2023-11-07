package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.RawMaterialDto;
import com.fsd.inventopilot.models.RawMaterial;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Component
public class RawMaterialDtoMapper {
    private final LocationDtoMapper locationDtoMapper;
    private final ProductDtoMapper productDtoMapper;

    public RawMaterialDtoMapper(LocationDtoMapper locationDtoMapper, ProductDtoMapper productDtoMapper) {
        this.locationDtoMapper = locationDtoMapper;
        this.productDtoMapper = productDtoMapper;
    }


    public RawMaterialDto mapToDto(RawMaterial rawMaterial) {
        RawMaterialDto dto = new RawMaterialDto();

        BeanUtils.copyProperties(rawMaterial, dto);
        dto.setLocations(rawMaterial.getLocations().stream()
                .map(locationDtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setProducts(rawMaterial.getProducts().stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    public RawMaterial mapToEntity(RawMaterialDto dto) {
        RawMaterial rawMaterial = new RawMaterial();

        BeanUtils.copyProperties(dto, rawMaterial);
        rawMaterial.setLocations(dto.getLocations().stream()
                .map(locationDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        rawMaterial.setProducts(dto.getProducts().stream()
                .map(productDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));

        return rawMaterial;
    }
}
