package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.AttachmentDto;
import com.fsd.inventopilot.dtos.AttachmentInputDto;
import com.fsd.inventopilot.exceptions.AttachmentStorageException;
import com.fsd.inventopilot.mappers.AttachmentDtoMapper;
import com.fsd.inventopilot.models.Attachment;
import com.fsd.inventopilot.repositories.AttachmentRepository;
import com.fsd.inventopilot.services.AttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentDtoMapper attachmentDtoMapper;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentDtoMapper attachmentDtoMapper) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentDtoMapper = attachmentDtoMapper;
    }

    @Transactional
    public AttachmentDto uploadAttachment(AttachmentInputDto inputDto) throws AttachmentStorageException {
        // Check if the file is an image
        if (!inputDto.isImage()) {
            // Handle the case where a non-image file is detected
            throw new AttachmentStorageException("Only image files are allowed!");
        }

        Attachment attachment = attachmentDtoMapper.mapToEntity(inputDto);

        try {
            Attachment savedAttachment = attachmentRepository.save(attachment);
            String downloadURL = buildDownloadURL(savedAttachment.getId());
            return attachmentDtoMapper.mapToDto(savedAttachment, downloadURL);
        } catch (Exception e) {
            throw new AttachmentStorageException("Could not store file " + inputDto.getFileName() + ". Please try again!", e);
        }
    }

    private String buildDownloadURL(UUID attachmentId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/app/files/download/")
                .path(String.valueOf(attachmentId))
                .toUriString();
    }

    @Transactional
    public Attachment downloadAttachment(UUID id) throws AttachmentStorageException {
        return attachmentRepository.findById(id)
                .orElseThrow(()-> new AttachmentStorageException("Attachment not found with id " + id));
    }
}
