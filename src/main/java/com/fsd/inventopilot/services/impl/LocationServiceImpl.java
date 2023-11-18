package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.LocationDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.mappers.LocationDtoMapper;
import com.fsd.inventopilot.models.*;
import com.fsd.inventopilot.repositories.LocationRepository;
import com.fsd.inventopilot.services.LocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationDtoMapper locationDtoMapper;

    public LocationServiceImpl(LocationRepository locationRepository, LocationDtoMapper locationDtoMapper) {
        this.locationRepository = locationRepository;
        this.locationDtoMapper = locationDtoMapper;
    }

    @Transactional(readOnly = true)
    public List<LocationDto> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(locationDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public LocationDto getLocationDetails(Department department) {
        Optional<Location> location = locationRepository.findByDepartment(department);
        if (location.isPresent()) {
            Location foundLocation = location.get();
            return locationDtoMapper.mapToDto(foundLocation);
        } else {
            throw new RecordNotFoundException("Location: " + department + " not found");
        }
    }

    @Transactional
    public LocationDto postLocation(LocationDto locationDto) {
        Location location = locationDtoMapper.mapToEntity(locationDto);
        locationRepository.save(location);
        return locationDtoMapper.mapToDto(location);
    }

    // will be updated when location details are finalized
    @Transactional
    public LocationDto updateLocation(Department department, LocationDto newLocation) {
        return locationRepository.findByDepartment(department)
                .map(existingLocation -> {
                    existingLocation.setDepartment(department);
                    locationRepository.save(existingLocation);
                    return locationDtoMapper.mapToDto(existingLocation);
                })
                .orElseThrow(() -> new RecordNotFoundException("Location: " + department + " not found"));
    }

    @Transactional
    public void deleteLocation(Department department) {
        Optional<Location> location = locationRepository.findByDepartment(department);
        if (location.isPresent()) {
            Location deleteLocation = location.get();
            locationRepository.delete(deleteLocation);
        } else {
            throw new RecordNotFoundException("Location: " + department + " not found");
        }
    }

    // will be updated when location details are finalized
    @Transactional
    public LocationDto updateLocationDetails(Department department, LocationDto updatedLocation) {
        Optional<Location> existingLocation = locationRepository.findByDepartment(department);
        if (existingLocation.isPresent()) {

            Location updatedLocationEntity = locationDtoMapper.mapToEntity(updatedLocation);
            updatedLocationEntity.setDepartment(department);
            locationRepository.save(updatedLocationEntity);
            return locationDtoMapper.mapToDto(updatedLocationEntity);
        } else {
            throw new RecordNotFoundException("Location: " + department + " not found");
        }
    }
}