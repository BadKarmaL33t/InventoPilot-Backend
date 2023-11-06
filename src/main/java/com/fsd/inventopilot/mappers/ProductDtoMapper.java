package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.ProductDto;
import com.fsd.inventopilot.models.Product;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {
    public static ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();

        BeanUtils.copyProperties(product, dto);
        dto.setLocations(product.getLocations().stream()
                .map(LocationDtoMapper::mapToDto)
                .collect(Collectors.toSet()));
        dto.setRawMaterial(RawMaterialDtoMapper.mapToDto(product.getRawMaterial()));
        dto.setComponents(product.getComponents().stream()
                .map(ProductComponentDtoMapper::mapToDto)
                .collect(Collectors.toSet()));

        return dto;
    }

    public static Product mapToEntity(ProductDto dto) {
        Product product = new Product();

        BeanUtils.copyProperties(dto, product);
        product.setLocations(dto.getLocations().stream()
                .map(LocationDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));
        product.setRawMaterial(RawMaterialDtoMapper.mapToEntity(dto.getRawMaterial()));
        product.setComponents(dto.getComponents().stream()
                .map(ProductComponentDtoMapper::mapToEntity)
                .collect(Collectors.toSet()));

        return product;
    }
}
