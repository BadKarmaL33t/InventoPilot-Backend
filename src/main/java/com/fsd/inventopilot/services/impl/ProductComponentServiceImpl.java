package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.ProductComponentDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.mappers.ProductComponentDtoMapper;
import com.fsd.inventopilot.models.Department;
import com.fsd.inventopilot.models.Location;
import com.fsd.inventopilot.models.ProductComponent;
import com.fsd.inventopilot.repositories.LocationRepository;
import com.fsd.inventopilot.repositories.ProductComponentRepository;
import com.fsd.inventopilot.services.ProductComponentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductComponentServiceImpl implements ProductComponentService {
    private final ProductComponentRepository componentRepository;
    private final ProductComponentDtoMapper componentDtoMapper;
    private final LocationRepository locationRepository;

    public ProductComponentServiceImpl(ProductComponentRepository componentRepository, ProductComponentDtoMapper componentDtoMapper, LocationRepository locationRepository) {
        this.componentRepository = componentRepository;
        this.componentDtoMapper = componentDtoMapper;
        this.locationRepository = locationRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductComponentDto> getAllComponents() {
        List<ProductComponent> components = componentRepository.findAll();
        return components.stream()
                .map(componentDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductComponentDto getComponentDetails(String name) {
        Optional<ProductComponent> productComponent = componentRepository.findByName(name);
        if (productComponent.isPresent()) {
            ProductComponent existingComponent = productComponent.get();
            return componentDtoMapper.mapToDto(existingComponent);
        } else {
            throw new RecordNotFoundException("Component: " + name + " not found");
        }
    }

    @Transactional
    public ProductComponentDto postComponent(ProductComponentDto componentDto) {
        ProductComponent component = componentDtoMapper.mapToEntity(componentDto);

        Optional<Location> warehouse = locationRepository.findByDepartment(Department.WAREHOUSE);
        if (warehouse.isPresent()) {
            Location location = warehouse.get();
            location.getComponents().add(component);
            locationRepository.save(location);
        } else {
            throw new RecordNotFoundException("Location warehouse could not be found");
        }

        componentRepository.save(component);
        return componentDtoMapper.mapToDto(component);
    }

    @Transactional
    public ProductComponentDto updateComponent(String name, ProductComponentDto newComponent) {
        return componentRepository.findByName(name)
                .map(existingComponent -> {
                    ProductComponent updatedComponent = componentDtoMapper.mapToEntity(newComponent);
                    updatedComponent.setName(name);
                    updatedComponent.setComponentType(newComponent.getComponentType());
                    updatedComponent.setStock(newComponent.getStock());
                    updatedComponent.setProductStatus(newComponent.getProductStatus());
                    updatedComponent.setSerialNumber(newComponent.getSerialNumber());
                    updatedComponent.setMinimalStock(newComponent.getMinimalStock());
                    updatedComponent.setMaximalStock(newComponent.getMaximalStock());

                    componentRepository.save(updatedComponent);
                    return componentDtoMapper.mapToDto(updatedComponent);
                })
                .orElseThrow(() -> new RecordNotFoundException("Component: " + name + " not found"));
    }

    @Transactional
    public void deleteComponent(String name) {
        Optional<ProductComponent> component = componentRepository.findByName(name);
        if (component.isPresent()) {
            ProductComponent existingComponent = component.get();
            componentRepository.delete(existingComponent);
        } else {
            throw new RecordNotFoundException("Component: " + name + " not found");
        }
    }

    @Transactional
    public ProductComponentDto updateComponentDetails(String name, ProductComponentDto updatedComponent) {
        Optional<ProductComponent> productComponent = componentRepository.findByName(name);
        if (productComponent.isPresent()) {
            ProductComponent existingComponent = productComponent.get();
            if (updatedComponent.getName() != null) {
                existingComponent.setName(updatedComponent.getName());
            }
            if (updatedComponent.getComponentType() != null) {
                existingComponent.setComponentType(updatedComponent.getComponentType());
            }
            if (updatedComponent.getStock() != 0) {
                existingComponent.setStock(updatedComponent.getStock());
            }
            if (updatedComponent.getProductStatus() != null) {
                existingComponent.setProductStatus(updatedComponent.getProductStatus());
            }
            if (updatedComponent.getSerialNumber() != null) {
                existingComponent.setSerialNumber(updatedComponent.getSerialNumber());
            }
            if (updatedComponent.getMinimalStock() != 0) {
                existingComponent.setMinimalStock(updatedComponent.getMinimalStock());
            }
            if (updatedComponent.getMaximalStock() != 0) {
                existingComponent.setMaximalStock(updatedComponent.getMaximalStock());
            }

            componentRepository.save(existingComponent);
            return componentDtoMapper.mapToDto(existingComponent);
        } else {
            throw new RecordNotFoundException("Component: " + name + " not found");
        }
    }
}
