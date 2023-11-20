package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.RawMaterialDto;
import com.fsd.inventopilot.models.RawMaterial;
import com.fsd.inventopilot.services.RawMaterialService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/app/raws")
public class RawMaterialController {
    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialDto>> getAllRawMaterials() {
        List<RawMaterialDto> rawMaterialDtos;
        rawMaterialDtos = rawMaterialService.getAllRawMaterials();

        return ResponseEntity.ok().body(rawMaterialDtos);
    }

    @GetMapping("/{name}")
    public ResponseEntity<RawMaterialDto> getRawMaterialDetails(@PathVariable("name") String name) {
        RawMaterialDto dto = rawMaterialService.getRawMaterialDetails(name);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<RawMaterialDto> postRawMaterial(@Valid @RequestBody RawMaterialDto rawMaterialDto) {
        RawMaterialDto dto = rawMaterialService.postRawMaterial(rawMaterialDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getName())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{name}")
    public ResponseEntity<RawMaterialDto> updateRawMaterial(@PathVariable String name,@Valid @RequestBody RawMaterialDto newRawMaterial) {
        RawMaterialDto dto = rawMaterialService.updateRawMaterial(name, newRawMaterial);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<RawMaterial> deleteProduct(@PathVariable String name) {
        rawMaterialService.deleteRawMaterial(name);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{name}")
    public ResponseEntity<RawMaterialDto> updateRawMaterialDetails(@PathVariable String name, @Valid @RequestBody RawMaterialDto updatedRawMaterial) {
        RawMaterialDto dto = rawMaterialService.updateRawMaterialDetails(name, updatedRawMaterial);

        return ResponseEntity.ok().body(dto);
    }
}
