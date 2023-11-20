package com.fsd.inventopilot.services;


import com.fsd.inventopilot.dtos.AttachmentDto;
import com.fsd.inventopilot.dtos.AttachmentInputDto;
import com.fsd.inventopilot.exceptions.AttachmentStorageException;
import com.fsd.inventopilot.models.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AttachmentService {
    AttachmentDto uploadAttachment(AttachmentInputDto inputDto) throws Exception;
    Attachment downloadAttachment(UUID id) throws Exception;
}
