package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.FileResponseData;
import com.fsd.inventopilot.models.Attachment;
import com.fsd.inventopilot.services.AttachmentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/app/files/upload/")
    public FileResponseData uploadAttachment(@RequestParam("file") MultipartFile file) throws Exception {
        Attachment attachment = attachmentService.uploadAttachment(file);
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getFileName())
                .toUriString();
        return new FileResponseData(
                attachment.getFileName(),
                downloadURL,
                file.getContentType(),
                file.getSize()
        );
    }

    @GetMapping("/app/files/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws Exception {
        Attachment attachment = attachmentService.downloadAttachment(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}
