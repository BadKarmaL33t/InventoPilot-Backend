package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.ProductComponentDto;
import com.fsd.inventopilot.models.ProductComponent;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;

import java.util.stream.Collectors;

@Component
public class ProductComponentDtoMapper {
    private final ApplicationContext applicationContext;

    public ProductComponentDtoMapper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ProductComponentDto mapToDto(ProductComponent component) {
        ProductComponentDto dto = new ProductComponentDto();

        BeanUtils.copyProperties(component, dto);
        dto.setLocations(component.getLocations().stream()
                .map(location -> getLocationDtoMapper().mapToDto(location))
                .collect(Collectors.toSet()));
        dto.setProducts(component.getProducts().stream()
                .map(product -> getProductDtoMapper().mapToDto(product))
                .collect(Collectors.toSet()));

        return dto;
    }

    public ProductComponent mapToEntity(ProductComponentDto dto) {
        ProductComponent component = new ProductComponent();

        BeanUtils.copyProperties(dto, component);
        component.setLocations(dto.getLocations().stream()
                .map(location -> getLocationDtoMapper().mapToEntity(location))
                .collect(Collectors.toSet()));
        component.setProducts(dto.getProducts().stream()
                .map(product -> getProductDtoMapper().mapToEntity(product))
                .collect(Collectors.toSet()));

        return component;
    }

    // Lazily initialize the mappers to prevent circular dependencies
    private LocationDtoMapper getLocationDtoMapper() {
        return applicationContext.getBean(LocationDtoMapper.class);
    }

    private ProductDtoMapper getProductDtoMapper() {
        return applicationContext.getBean(ProductDtoMapper.class);
    }
}