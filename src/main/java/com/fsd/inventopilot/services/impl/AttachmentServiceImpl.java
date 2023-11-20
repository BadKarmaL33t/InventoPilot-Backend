package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.exceptions.AttachmentStorageException;
import com.fsd.inventopilot.models.Attachment;
import com.fsd.inventopilot.repositories.AttachmentRepository;
import com.fsd.inventopilot.services.AttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Transactional
    public Attachment uploadAttachment(MultipartFile file) throws Exception {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (filename.contains("..")) {
                throw new AttachmentStorageException("Filename contains invalid path sequence " + filename);
            }
            Attachment attachment = new Attachment(
                    filename,
                    file.getContentType(),
                    file.getBytes()
            );
            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            throw new AttachmentStorageException("Could not store file " + filename + ". Please try again!", e);
        }
    }

    public Attachment downloadAttachment(Long id) throws Exception {
        return attachmentRepository.findById(id)
                .orElseThrow(()-> new Exception("Attachment not found with id " + id));
    }
}
