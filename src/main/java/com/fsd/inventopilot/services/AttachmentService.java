package com.fsd.inventopilot.services;


import com.fsd.inventopilot.models.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Attachment uploadAttachment(MultipartFile file) throws Exception;
    Attachment downloadAttachment(Long id) throws Exception;
}
