package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, String> {
    RawMaterial findByName(String name);
}
