package com.fsd.inventopilot.controllers;

import com.fsd.inventopilot.dtos.FileResponseData;
import com.fsd.inventopilot.models.Attachment;
import com.fsd.inventopilot.services.AttachmentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/app/files")
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponseData> uploadAttachment(@RequestParam("file") MultipartFile file) throws Exception {
        Attachment attachment = attachmentService.uploadAttachment(file);
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/app/files/download/")
                .path(String.valueOf(attachment.getId()))
                .toUriString();
        return ResponseEntity.ok().body(new FileResponseData(
                attachment.getFileName(),
                downloadURL,
                file.getContentType(),
                file.getSize()
        ));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable UUID id) throws Exception {
        Attachment attachment = attachmentService.downloadAttachment(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .body(new ByteArrayResource(attachment.getData()));
    }
}
