package com.fsd.inventopilot.services;

import com.fsd.inventopilot.dtos.RawMaterialDto;

import java.util.List;

public interface RawMaterialService {
    List<RawMaterialDto> getAllRawMaterials();
    RawMaterialDto getRawMaterialDetails(String name);
    RawMaterialDto postRawMaterial(RawMaterialDto rawMaterialDto);
    RawMaterialDto updateRawMaterial(String name, RawMaterialDto newRawMaterial);
    void deleteRawMaterial(String name);
    RawMaterialDto updateRawMaterialDetails(String name, RawMaterialDto updatedRawMaterial);
}
