package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository extends JpaRepository<Component, String> {

}
