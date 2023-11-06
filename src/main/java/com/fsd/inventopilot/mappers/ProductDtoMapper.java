package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.ProductDto;
import com.fsd.inventopilot.models.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {
    public static ProductDto mapToDto(Product product) {
        ProductDto dto = new ProductDto();

        dto.setName(product.getName());
        dto.setType(product.getType());
        dto.setStock(product.getStock());
        dto.setSold(product.getSold());
        dto.setSerialNumber(product.getSerialNumber());
        dto.setMinimalStock(product.getMinimalStock());
        dto.setMaximalStock(product.getMaximalStock());
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

        product.setName(dto.getName());
        product.setType(dto.getType());
        product.setStock(dto.getStock());
        product.setSold(dto.getSold());
        product.setSerialNumber(dto.getSerialNumber());
        product.setMinimalStock(dto.getMinimalStock());
        product.setMaximalStock(dto.getMaximalStock());
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
