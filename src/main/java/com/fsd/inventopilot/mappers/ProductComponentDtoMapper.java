package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.ComponentDto;
import com.fsd.inventopilot.models.ProductComponent;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Component
public class ProductComponentDtoMapper {
    public static ComponentDto mapToDto(ProductComponent component) {
        ComponentDto dto = new ComponentDto();

        BeanUtils.copyProperties(component, dto);
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

        BeanUtils.copyProperties(dto, component);
        component.setLocations(dto.getLocations().stream()
                .map(LocationDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        component.setProducts(dto.getProducts().stream()
                .map(ProductDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));

        return component;
    }
}