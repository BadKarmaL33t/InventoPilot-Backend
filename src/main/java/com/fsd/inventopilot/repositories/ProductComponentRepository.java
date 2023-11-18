package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.ProductComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductComponentRepository extends JpaRepository<ProductComponent, String> {
    Optional<ProductComponent> findByName(String name);
}
