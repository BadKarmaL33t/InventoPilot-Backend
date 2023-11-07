package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.LocationDto;
import com.fsd.inventopilot.exceptions.RecordNotFoundException;
import com.fsd.inventopilot.mappers.LocationDtoMapper;
import com.fsd.inventopilot.models.Department;
import com.fsd.inventopilot.models.Location;
import com.fsd.inventopilot.repositories.LocationRepository;
import com.fsd.inventopilot.services.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationDtoMapper locationDtoMapper;

    public LocationServiceImpl(LocationRepository locationRepository, LocationDtoMapper locationDtoMapper) {
        this.locationRepository = locationRepository;
        this.locationDtoMapper = locationDtoMapper;
    }

    public List<LocationDto> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(locationDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public LocationDto getLocationDtoByDepartment(Department department) {
        Location location = locationRepository.findByDepartment(department);
        if (location != null) {
            return locationDtoMapper.mapToDto(location);
        } else {
            throw new RecordNotFoundException("Location: " + department + " not found");
        }
    }

    public LocationDto postLocation(LocationDto locationDto) {
        Location location = locationDtoMapper.mapToEntity(locationDto);
        locationRepository.save(location);
        return locationDtoMapper.mapToDto(location);
    }

    public LocationDto updateLocation(Department department, LocationDto newLocationDto) {
        Location existingLocation = locationRepository.findByDepartment(department);
        if (existingLocation != null) {
            Location updatedLocation = locationDtoMapper.mapToEntity(newLocationDto);
            updatedLocation.setDepartment(department);
            locationRepository.save(updatedLocation);
            return locationDtoMapper.mapToDto(updatedLocation);
        } else {
            throw new RecordNotFoundException("Location: " + department + " not found");
        }
    }

    public void deleteLocation(Department department) {
        Location location = locationRepository.findByDepartment(department);
        if (location != null) {
            locationRepository.delete(location);
        } else {
            throw new RecordNotFoundException("Location: " + department + " not found");
        }
    }

    public LocationDto updateLocationDetails(Department department, LocationDto updatedLocation) {
        Location existingLocation = locationRepository.findByDepartment(department);
        if (existingLocation != null) {
            Location updatedLocationEntity = locationDtoMapper.mapToEntity(updatedLocation);
            updatedLocationEntity.setDepartment(department);
            locationRepository.save(updatedLocationEntity);
            return locationDtoMapper.mapToDto(updatedLocationEntity);
        } else {
            throw new RecordNotFoundException("Location: " + department + " not found");
        }
    }
}