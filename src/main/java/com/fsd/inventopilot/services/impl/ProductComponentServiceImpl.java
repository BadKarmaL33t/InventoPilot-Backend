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
        ProductComponent existingComponent = componentRepository.findByName(name);
        if (existingComponent != null) {
            return componentDtoMapper.mapToDto(existingComponent);
        }
        throw new RecordNotFoundException("Component: " + name + " not found");
    }

    @Transactional
    public ProductComponentDto postComponent(ProductComponentDto componentDto) {
        ProductComponent component = componentDtoMapper.mapToEntity(componentDto);

        Location warehouse = locationRepository.findByDepartment(Department.WAREHOUSE);
        if (warehouse != null) {
            warehouse.getComponents().add(component);
        } else {
            throw new RecordNotFoundException("Location warehouse could not be found");
        }

        componentRepository.save(component);
        return componentDtoMapper.mapToDto(component);
    }

    @Transactional
    public ProductComponentDto updateComponent(String name, ProductComponentDto newComponent) {
        ProductComponent existingComponent = componentRepository.findByName(name);
        if (existingComponent != null) {
            ProductComponent updatedComponent = componentDtoMapper.mapToEntity(newComponent);
            updatedComponent.setName(name);
            updatedComponent.setType(newComponent.getType());
            updatedComponent.setStock(newComponent.getStock());
            updatedComponent.setSerialNumber(newComponent.getSerialNumber());
            updatedComponent.setMinimalStock(newComponent.getMinimalStock());
            updatedComponent.setMaximalStock(newComponent.getMaximalStock());

            componentRepository.save(updatedComponent);
            return componentDtoMapper.mapToDto(updatedComponent);
        }
        throw new RecordNotFoundException("Component: " + name + " not found");
    }

    @Transactional
    public void deleteComponent(String name) {
        ProductComponent component = componentRepository.findByName(name);
        if (component != null) {
            componentRepository.delete(component);
        }
        throw new RecordNotFoundException("Component: " + name + " not found");
    }

    @Transactional
    public ProductComponentDto updateComponentDetails(String name, ProductComponentDto updatedComponent) {
        ProductComponent existingComponent = componentRepository.findByName(name);
        if (existingComponent != null) {
            if (updatedComponent.getName() != null) {
                existingComponent.setName(updatedComponent.getName());
            }
            if (updatedComponent.getType() != null) {
                existingComponent.setType(updatedComponent.getType());
            }
            if (updatedComponent.getStock() != 0) {
                existingComponent.setStock(updatedComponent.getStock());
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
        }
        throw new RecordNotFoundException("Component: " + name + " not found");
    }
}
