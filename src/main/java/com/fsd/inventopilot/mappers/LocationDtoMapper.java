package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.LocationDto;
import com.fsd.inventopilot.models.Location;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Component
public class LocationDtoMapper {
    private final ApplicationContext applicationContext;

    public LocationDtoMapper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public LocationDto mapToDto(Location location) {
        LocationDto dto = new LocationDto();

        BeanUtils.copyProperties(location, dto);
        dto.setComposites(location.getComponents().stream()
                .map(component -> {
                    ProductComponentDtoMapper mapper = getProductComponentDtoMapper();
                    return mapper.mapToDto(component);
                })
                .collect(Collectors.toSet()));
        dto.setProducts(location.getProducts().stream()
                .map(product -> {
                    ProductDtoMapper mapper = getProductDtoMapper();
                    return mapper.mapToDto(product);
                })
                .collect(Collectors.toSet()));
        dto.setRawMaterials(location.getRawMaterials().stream()
                .map(rawMaterial -> {
                    RawMaterialDtoMapper mapper = getRawMaterialDtoMapper();
                    return mapper.mapToDto(rawMaterial);
                })
                .collect(Collectors.toSet()));

        return dto;
    }

    public Location mapToEntity(LocationDto dto) {
        Location location = new Location();

        BeanUtils.copyProperties(dto, location);
        location.setComponents(dto.getComposites().stream()
                .map(component -> {
                    ProductComponentDtoMapper mapper = getProductComponentDtoMapper();
                    return mapper.mapToEntity(component);
                })
                .collect(Collectors.toSet()));
        location.setProducts(dto.getProducts().stream()
                .map(product -> {
                    ProductDtoMapper mapper = getProductDtoMapper();
                    return mapper.mapToEntity(product);
                })
                .collect(Collectors.toSet()));
        location.setRawMaterials(dto.getRawMaterials().stream()
                .map(rawMaterial -> {
                    RawMaterialDtoMapper mapper = getRawMaterialDtoMapper();
                    return mapper.mapToEntity(rawMaterial);
                })
                .collect(Collectors.toSet()));

        return location;
    }

    // Lazily initialize the mappers to prevent circular dependencies
    private ProductComponentDtoMapper getProductComponentDtoMapper() {
        return applicationContext.getBean(ProductComponentDtoMapper.class);
    }

    private RawMaterialDtoMapper getRawMaterialDtoMapper() {
        return applicationContext.getBean(RawMaterialDtoMapper.class);
    }

    private ProductDtoMapper getProductDtoMapper() {
        return applicationContext.getBean(ProductDtoMapper.class);
    }
}