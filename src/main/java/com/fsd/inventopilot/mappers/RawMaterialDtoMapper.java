package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.RawMaterialDto;
import com.fsd.inventopilot.models.RawMaterial;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;

import java.util.stream.Collectors;

@Component
public class RawMaterialDtoMapper {
    private final ApplicationContext applicationContext;

    public RawMaterialDtoMapper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public RawMaterialDto mapToDto(RawMaterial rawMaterial) {
        RawMaterialDto dto = new RawMaterialDto();

        BeanUtils.copyProperties(rawMaterial, dto);
        dto.setLocations(rawMaterial.getLocations().stream()
                .map(location -> getLocationDtoMapper().mapToDto(location))
                .collect(Collectors.toSet()));
        dto.setProducts(rawMaterial.getProducts().stream()
                .map(product -> getProductDtoMapper().mapToDto(product))
                .collect(Collectors.toSet()));

        return dto;
    }

    public RawMaterial mapToEntity(RawMaterialDto dto) {
        RawMaterial rawMaterial = new RawMaterial();

        BeanUtils.copyProperties(dto, rawMaterial);
        rawMaterial.setLocations(dto.getLocations().stream()
                .map(location -> getLocationDtoMapper().mapToEntity(location))
                .collect(Collectors.toSet()));
        rawMaterial.setProducts(dto.getProducts().stream()
                .map(product -> getProductDtoMapper().mapToEntity(product))
                .collect(Collectors.toSet()));

        return rawMaterial;
    }

    // Lazily initialize the mappers to prevent circular dependencies
    private LocationDtoMapper getLocationDtoMapper() {
        return applicationContext.getBean(LocationDtoMapper.class);
    }

    private ProductDtoMapper getProductDtoMapper() {
        return applicationContext.getBean(ProductDtoMapper.class);
    }
}
