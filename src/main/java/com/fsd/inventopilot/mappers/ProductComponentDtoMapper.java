package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.ComponentDto;
import com.fsd.inventopilot.models.ProductComponent;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class ProductComponentDtoMapper {
    public static ComponentDto mapToDto(ProductComponent component) {
        ComponentDto dto = new ComponentDto();

        dto.setName(component.getName());
        dto.setType(component.getType());
        dto.setQuantity(component.getQuantity());
        dto.setSerialNumber(component.getSerialNumber());
        dto.setMinimalStock(component.getMinimalStock());
        dto.setMaximalStock(component.getMaximalStock());
        dto.setLocations(component.getLocations().stream()
                .map(LocationDtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setProducts(component.getProducts().stream()
                .map(ProductDtoMapper::mapToDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    public static ProductComponent mapToEntity(ComponentDto dto) {
        ProductComponent component = new ProductComponent();

        component.setName(dto.getName());
        component.setType(dto.getType());
        component.setQuantity(dto.getQuantity());
        component.setSerialNumber(dto.getSerialNumber());
        component.setMinimalStock(dto.getMinimalStock());
        component.setMaximalStock(dto.getMaximalStock());
        component.setLocations(dto.getLocations().stream()
                .map(LocationDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        component.setProducts(dto.getProducts().stream()
                .map(ProductDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));

        return component;
    }
}
