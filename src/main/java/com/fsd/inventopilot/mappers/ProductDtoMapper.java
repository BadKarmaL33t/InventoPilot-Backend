package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.ProductDto;
import com.fsd.inventopilot.models.Product;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {
    private final LocationDtoMapper locationDtoMapper;
    private final ProductComponentDtoMapper productComponentDtoMapper;
    private final RawMaterialDtoMapper rawMaterialDtoMapper;

    public ProductDtoMapper(LocationDtoMapper locationDtoMapper, ProductComponentDtoMapper productComponentDtoMapper, RawMaterialDtoMapper rawMaterialDtoMapper) {
        this.locationDtoMapper = locationDtoMapper;
        this.productComponentDtoMapper = productComponentDtoMapper;
        this.rawMaterialDtoMapper = rawMaterialDtoMapper;
    }

    public ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();

        BeanUtils.copyProperties(product, dto);
        dto.setLocations(product.getLocations().stream()
                .map(locationDtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setRawMaterial(rawMaterialDtoMapper.mapToDto(product.getRawMaterial()));
        dto.setComponents(product.getComponents().stream()
                .map(productComponentDtoMapper::mapToDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    public Product mapToEntity(ProductDto dto) {
        Product product = new Product();

        BeanUtils.copyProperties(dto, product);
        product.setLocations(dto.getLocations().stream()
                .map(locationDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        product.setRawMaterial(rawMaterialDtoMapper.mapToEntity(dto.getRawMaterial()));
        product.setComponents(dto.getComponents().stream()
                .map(productComponentDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));

        return product;
    }
}
