package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttachmentEntityRepository extends JpaRepository<AttachmentEntity, UUID> {
}