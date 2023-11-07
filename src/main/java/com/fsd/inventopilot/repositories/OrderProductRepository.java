package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
