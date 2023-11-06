package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
