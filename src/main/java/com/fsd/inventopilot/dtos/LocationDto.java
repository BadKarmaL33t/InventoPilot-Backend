package com.fsd.inventopilot.dtos;

import com.fsd.inventopilot.models.Department;
import com.fsd.inventopilot.validations.AllowedDepartment;
import lombok.Data;

import java.util.Collection;

@Data
public class LocationDto {
    @AllowedDepartment
    private Department department;
    private Collection<String> compositeNames;
    private Collection<String> rawMaterialNames;
    private Collection<String> productNames;
}