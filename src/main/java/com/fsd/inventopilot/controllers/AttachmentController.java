package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.AttachmentDto;
import com.fsd.inventopilot.dtos.AttachmentInputDto;
import com.fsd.inventopilot.models.Attachment;
import com.fsd.inventopilot.services.AttachmentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/app/files")
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<AttachmentDto> uploadAttachment(@RequestPart MultipartFile file) {
        try {
            AttachmentInputDto inputDto = new AttachmentInputDto(
                    StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())),
                    file.getContentType(),
                    file.getBytes()
            );

            AttachmentDto dto = attachmentService.uploadAttachment(inputDto);

            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable UUID id) throws Exception {
        Attachment attachment = attachmentService.downloadAttachment(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .body(new ByteArrayResource(attachment.getData()));
    }
}
