package com.fsd.inventopilot.repositories;

import com.fsd.inventopilot.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
