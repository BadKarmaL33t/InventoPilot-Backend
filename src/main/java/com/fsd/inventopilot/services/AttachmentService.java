package com.fsd.inventopilot.services;


import com.fsd.inventopilot.models.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AttachmentService {
    Attachment uploadAttachment(MultipartFile file) throws Exception;
    Attachment downloadAttachment(UUID id) throws Exception;
}
