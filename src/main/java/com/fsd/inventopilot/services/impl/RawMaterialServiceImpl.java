package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.RawMaterialDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.mappers.RawMaterialDtoMapper;
import com.fsd.inventopilot.models.RawMaterial;
import com.fsd.inventopilot.repositories.RawMaterialRepository;
import com.fsd.inventopilot.services.RawMaterialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RawMaterialServiceImpl implements RawMaterialService {
    private final RawMaterialRepository rawMaterialRepository;
    private final RawMaterialDtoMapper rawMaterialDtoMapper;

    public RawMaterialServiceImpl(RawMaterialRepository rawMaterialRepository, RawMaterialDtoMapper rawMaterialDtoMapper) {
        this.rawMaterialRepository = rawMaterialRepository;
        this.rawMaterialDtoMapper = rawMaterialDtoMapper;
    }

    @Transactional(readOnly = true)
    public List<RawMaterialDto> getAllRawMaterials() {
        List<RawMaterial> rawMaterials = rawMaterialRepository.findAll();
        return rawMaterials.stream()
                .map(rawMaterialDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RawMaterialDto getRawMaterialDetails(String name) {
        RawMaterial existingRawMaterial = rawMaterialRepository.findByName(name);
        if (existingRawMaterial != null) {
            return rawMaterialDtoMapper.mapToDto(existingRawMaterial);
        } else {
            throw new RecordNotFoundException("RawMaterial: " + name + " not found");
        }
    }

    @Transactional
    public RawMaterialDto postRawMaterial(RawMaterialDto rawMaterialDto) {
        RawMaterial rawMaterial = rawMaterialDtoMapper.mapToEntity(rawMaterialDto);
        rawMaterialRepository.save(rawMaterial);
        return rawMaterialDtoMapper.mapToDto(rawMaterial);
    }

    @Transactional
    public RawMaterialDto updateRawMaterial(String name, RawMaterialDto newRawMaterial) {
        RawMaterial existingRawMaterial = rawMaterialRepository.findByName(name);
        if (existingRawMaterial != null) {
            RawMaterial updatedRawMaterialEntity = rawMaterialDtoMapper.mapToEntity(newRawMaterial);
            updatedRawMaterialEntity.setName(name);
            updatedRawMaterialEntity.setQuantity(newRawMaterial.getQuantity());
            updatedRawMaterialEntity.setBatchNumber(newRawMaterial.getBatchNumber());
            updatedRawMaterialEntity.setMinimalStock(newRawMaterial.getMinimalStock());
            updatedRawMaterialEntity.setMaximalStock(newRawMaterial.getMaximalStock());

            rawMaterialRepository.save(updatedRawMaterialEntity);
            return rawMaterialDtoMapper.mapToDto(updatedRawMaterialEntity);
        } else {
            throw new RecordNotFoundException("RawMaterial: " + name + " not found");
        }
    }

    @Transactional
    public void deleteRawMaterial(String name) {
        RawMaterial rawMaterial = rawMaterialRepository.findByName(name);
        if (rawMaterial != null) {
            rawMaterialRepository.delete(rawMaterial);
        } else {
            throw new RecordNotFoundException("RawMaterial: " + name + " not found");
        }
    }

    @Transactional
    public RawMaterialDto updateRawMaterialDetails(String name, RawMaterialDto updatedRawMaterial) {
        RawMaterial existingRawMaterial = rawMaterialRepository.findByName(name);
        if (existingRawMaterial != null) {
            if (updatedRawMaterial.getQuantity() != 0) {
                existingRawMaterial.setQuantity(updatedRawMaterial.getQuantity());
            }
            if (updatedRawMaterial.getBatchNumber() != null) {
                existingRawMaterial.setBatchNumber(updatedRawMaterial.getBatchNumber());
            }
            if (updatedRawMaterial.getMinimalStock() != 0) {
                existingRawMaterial.setMinimalStock(updatedRawMaterial.getMinimalStock());
            }
            if (updatedRawMaterial.getMaximalStock() != 0) {
                existingRawMaterial.setMaximalStock(updatedRawMaterial.getMaximalStock());
            }

            rawMaterialRepository.save(existingRawMaterial);
            return rawMaterialDtoMapper.mapToDto(existingRawMaterial);
        } else {
            throw new RecordNotFoundException("RawMaterial: " + name + " not found");
        }
    }
}