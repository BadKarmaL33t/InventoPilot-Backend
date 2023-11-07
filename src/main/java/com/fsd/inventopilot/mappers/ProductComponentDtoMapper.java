package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.ProductComponentDto;
import com.fsd.inventopilot.models.ProductComponent;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Component
public class ProductComponentDtoMapper {
    private final LocationDtoMapper locationDtoMapper;
    private final ProductDtoMapper productDtoMapper;

    public ProductComponentDtoMapper(LocationDtoMapper locationDtoMapper, ProductDtoMapper productDtoMapper) {
        this.locationDtoMapper = locationDtoMapper;
        this.productDtoMapper = productDtoMapper;
    }


    public ProductComponentDto mapToDto(ProductComponent component) {
        ProductComponentDto dto = new ProductComponentDto();

        BeanUtils.copyProperties(component, dto);
        dto.setLocations(component.getLocations().stream()
                .map(locationDtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setProducts(component.getProducts().stream()
                .map(productDtoMapper::mapToDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    public ProductComponent mapToEntity(ProductComponentDto dto) {
        ProductComponent component = new ProductComponent();

        BeanUtils.copyProperties(dto, component);
        component.setLocations(dto.getLocations().stream()
                .map(locationDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        component.setProducts(dto.getProducts().stream()
                .map(productDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));

        return component;
    }
}