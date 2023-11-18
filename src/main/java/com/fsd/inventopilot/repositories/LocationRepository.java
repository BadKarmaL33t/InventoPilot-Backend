package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.Department;
import com.fsd.inventopilot.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<Location> findByDepartment(Department department);
}
