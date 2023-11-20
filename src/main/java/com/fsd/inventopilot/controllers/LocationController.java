package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.LocationDto;
import com.fsd.inventopilot.models.Department;
import com.fsd.inventopilot.models.Location;
import com.fsd.inventopilot.services.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/app/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> locationDtos;
        locationDtos = locationService.getAllLocations();

        return ResponseEntity.ok().body(locationDtos);
    }

    @GetMapping("/{department}")
    public ResponseEntity<LocationDto> getLocationDetails(@PathVariable("department") Department department) {
        LocationDto dto = locationService.getLocationDetails(department);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<LocationDto> postLocation(@Valid @RequestBody LocationDto locationDto) {
        LocationDto dto = locationService.postLocation(locationDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getDepartment())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{department}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable Department department, @Valid @RequestBody LocationDto newLocation) {
        LocationDto dto = locationService.updateLocation(department, newLocation);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{department}")
    public ResponseEntity<Location> deleteLocation(@PathVariable Department department) {
        locationService.deleteLocation(department);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{department}")
    public ResponseEntity<LocationDto> updateLocationDetails(@PathVariable Department department, @Valid @RequestBody LocationDto updatedLocation) {
        LocationDto dto = locationService.updateLocationDetails(department, updatedLocation);

        return ResponseEntity.ok().body(dto);
    }
}
