package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.LocationDto;
import com.fsd.inventopilot.models.Location;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Component
public class LocationDtoMapper {
    private final ProductComponentDtoMapper productComponentDtoMapper;
    private final RawMaterialDtoMapper rawMaterialDtoMapper;
    private final ProductDtoMapper productDtoMapper;

    public LocationDtoMapper(
            ProductComponentDtoMapper productComponentDtoMapper,
            ProductDtoMapper productDtoMapper,
            RawMaterialDtoMapper rawMaterialDtoMapper
    ) {
        this.productComponentDtoMapper = productComponentDtoMapper;
        this.rawMaterialDtoMapper = rawMaterialDtoMapper;
        this.productDtoMapper = productDtoMapper;
    }

    public LocationDto mapToDto(Location location) {
        LocationDto dto = new LocationDto();

        BeanUtils.copyProperties(location, dto);
        dto.setComposites(location.getComponents().stream()
                .map(productComponentDtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setProducts(location.getProducts().stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setRawMaterials(location.getRawMaterials().stream()
                .map(rawMaterialDtoMapper::mapToDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    public Location mapToEntity(LocationDto dto) {
        Location location = new Location();

        BeanUtils.copyProperties(dto, location);
        location.setComponents(dto.getComposites().stream()
                .map(productComponentDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        location.setProducts(dto.getProducts().stream()
                .map(productDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        location.setRawMaterials(dto.getRawMaterials().stream()
                .map(rawMaterialDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));

        return location;
    }
}
