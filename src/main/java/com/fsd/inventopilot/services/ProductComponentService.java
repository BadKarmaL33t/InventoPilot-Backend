package com.fsd.inventopilot.services;

import com.fsd.inventopilot.dtos.ProductComponentDto;

import java.util.List;

public interface ProductComponentService {
    List<ProductComponentDto> getAllComponents();
    ProductComponentDto getComponentDetails(String name);
    ProductComponentDto postComponent(ProductComponentDto componentDto);
    ProductComponentDto updateComponent(String name, ProductComponentDto newComponent);
    void deleteComponent(String name);
    ProductComponentDto updateComponentDetails(String name, ProductComponentDto updatedComponent);
}
