package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.LocationDto;
import com.fsd.inventopilot.models.Location;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LocationDtoMapper {
    public static LocationDto mapToDto(Location location) {
        LocationDto dto = new LocationDto();

        dto.setDepartment(location.getDepartment());
        dto.setLocation(location.getLocation());
        dto.setComposites(location.getComposites().stream()
                .map(ProductComponentDtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setProducts(location.getProducts().stream()
                .map(ProductDtoMapper::mapToDto)
                .collect(Collectors.toSet()));

        return dto;
    }
    public static Location mapToEntity(LocationDto dto) {
        Location location = new Location();

        location.setDepartment(dto.getDepartment());
        location.setLocation(dto.getLocation());
        location.setComposites(dto.getComposites().stream()
                .map(ProductComponentDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        location.setProducts(dto.getProducts().stream()
                .map(ProductDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));

        return location;
    }
}
