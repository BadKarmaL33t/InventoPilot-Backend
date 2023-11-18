package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByName(String name);
    List<Product> findByProductType(ProductType productType);
}
