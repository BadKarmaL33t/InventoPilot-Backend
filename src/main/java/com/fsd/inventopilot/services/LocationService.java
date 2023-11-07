package com.fsd.inventopilot.services;

import com.fsd.inventopilot.dtos.LocationDto;
import com.fsd.inventopilot.models.Department;

import java.util.List;

public interface LocationService {
    List<LocationDto> getAllLocations();
    LocationDto getLocationDtoByDepartment(Department department);
    LocationDto postLocation(LocationDto locationDto);
    LocationDto updateLocation(Department department, LocationDto newLocation);
    void deleteLocation(Department department);
    LocationDto updateLocationDetails(Department department, LocationDto updatedLocation);
}