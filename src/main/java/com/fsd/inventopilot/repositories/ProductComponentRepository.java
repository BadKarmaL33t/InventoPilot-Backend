package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.ProductComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductComponentRepository extends JpaRepository<ProductComponent, String> {
    ProductComponent findByName(String name);
}
