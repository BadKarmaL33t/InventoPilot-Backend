package com.fsd.inventopilot.services;


import com.fsd.inventopilot.dtos.AttachmentDto;
import com.fsd.inventopilot.dtos.AttachmentInputDto;
import com.fsd.inventopilot.models.Attachment;

import java.util.UUID;

public interface AttachmentService {
    AttachmentDto addImageToEntity(String username, String identifier, AttachmentInputDto inputDto) throws Exception;
    Attachment downloadAttachment(UUID id) throws Exception;
}
