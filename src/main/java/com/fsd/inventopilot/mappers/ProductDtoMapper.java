package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.ProductDto;
import com.fsd.inventopilot.models.Product;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;

import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {
    private final ApplicationContext applicationContext;

    public ProductDtoMapper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();

        BeanUtils.copyProperties(product, dto);
        dto.setLocations(product.getLocations().stream()
                .map(location -> getLocationDtoMapper().mapToDto(location))
                .collect(Collectors.toSet()));
        dto.setRawMaterial(getRawMaterialDtoMapper().mapToDto(product.getRaw()));
        dto.setComponents(product.getComponents().stream()
                .map(productComponent -> getProductComponentDtoMapper().mapToDto(productComponent))
                .collect(Collectors.toSet()));

        return dto;
    }

    public Product mapToEntity(ProductDto dto) {
        Product product = new Product();

        BeanUtils.copyProperties(dto, product);
        product.setLocations(dto.getLocations().stream()
                .map(location -> getLocationDtoMapper().mapToEntity(location))
                .collect(Collectors.toSet()));
        product.setRaw(getRawMaterialDtoMapper().mapToEntity(dto.getRawMaterial()));
        product.setComponents(dto.getComponents().stream()
                .map(productComponent -> getProductComponentDtoMapper().mapToEntity(productComponent))
                .collect(Collectors.toSet()));

        return product;
    }

    // Lazily initialize the mappers to prevent circular dependencies
    private LocationDtoMapper getLocationDtoMapper() {
        return applicationContext.getBean(LocationDtoMapper.class);
    }

    private RawMaterialDtoMapper getRawMaterialDtoMapper() {
        return applicationContext.getBean(RawMaterialDtoMapper.class);
    }

    private ProductComponentDtoMapper getProductComponentDtoMapper() {
        return applicationContext.getBean(ProductComponentDtoMapper.class);
    }
}