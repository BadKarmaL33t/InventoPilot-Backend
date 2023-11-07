package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.ProductComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository<ProductComponent, String> {

}
