package com.fsd.inventopilot.mappers;

import com.fsd.inventopilot.dtos.AttachmentDto;
import com.fsd.inventopilot.dtos.AttachmentInputDto;
import com.fsd.inventopilot.models.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentDtoMapper {

    public Attachment mapToEntity(AttachmentInputDto inputDto) {
        return new Attachment(inputDto.getFileName(), inputDto.getContentType(), inputDto.getData());
    }

    public AttachmentDto mapToDto(Attachment attachment, String downloadURL) {
        return new AttachmentDto(attachment.getFileName(), downloadURL, attachment.getContentType());
    }
}
