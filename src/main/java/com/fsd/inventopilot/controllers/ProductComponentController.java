package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.ProductComponentDto;
import com.fsd.inventopilot.models.ProductComponent;
import com.fsd.inventopilot.services.ProductComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/app/components")
public class ProductComponentController {
    private final ProductComponentService componentService;

    public ProductComponentController(ProductComponentService componentService) {
        this.componentService = componentService;
    }


    @GetMapping
    public ResponseEntity<List<ProductComponentDto>> getAllComponents() {
        List<ProductComponentDto> componentDtos;
        componentDtos = componentService.getAllComponents();

        return ResponseEntity.ok().body(componentDtos);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductComponentDto> getComponentDetails(@PathVariable("name") String name) {
        ProductComponentDto dto = componentService.getComponentDetails(name);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProductComponentDto> postComponent(@RequestBody ProductComponentDto componentDto) {
        ProductComponentDto dto = componentService.postComponent(componentDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getName())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{name}")
    public ResponseEntity<ProductComponentDto> updateComponent(@PathVariable String name, @RequestBody ProductComponentDto newComponent) {
        ProductComponentDto dto = componentService.updateComponent(name, newComponent);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ProductComponent> deleteComponent(@PathVariable String name) {
        componentService.deleteComponent(name);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{name}")
    public ResponseEntity<ProductComponentDto> updateComponentDetails(@PathVariable String name, @RequestBody ProductComponentDto updatedComponent) {
        ProductComponentDto dto = componentService.updateComponentDetails(name, updatedComponent);

        return ResponseEntity.ok().body(dto);
    }
}
