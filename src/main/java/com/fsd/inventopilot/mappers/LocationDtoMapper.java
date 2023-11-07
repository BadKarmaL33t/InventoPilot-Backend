package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.LocationDto;
import com.fsd.inventopilot.models.Location;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Component
public class LocationDtoMapper {
    public static LocationDto mapToDto(Location location) {
        LocationDto dto = new LocationDto();

        BeanUtils.copyProperties(location, dto);
        dto.setComposites(location.getComponents().stream()
                .map(ProductComponentDtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setProducts(location.getProducts().stream()
                .map(ProductDtoMapper::mapToDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    public static Location mapToEntity(LocationDto dto) {
        Location location = new Location();

        BeanUtils.copyProperties(dto, location);
        location.setComponents(dto.getComposites().stream()
                .map(ProductComponentDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        location.setProducts(dto.getProducts().stream()
                .map(ProductDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));

        return location;
    }
}
