package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial, String> {
    Optional<RawMaterial> findByName(String name);
}
