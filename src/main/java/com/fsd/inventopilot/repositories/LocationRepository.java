package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {

}
