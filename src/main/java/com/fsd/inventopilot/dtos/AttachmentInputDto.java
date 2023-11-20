package com.fsd.inventopilot.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class AttachmentInputDto {
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private UUID id;
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private String fileName;
    @Pattern(regexp = "(\\S+(\\.(?i)(jpe?g|png|gif|bmp))$)")
    private String contentType;
    private byte[] data;

    public AttachmentInputDto(String filename, String contentType, byte[] bytes) {
        this.fileName = filename;
        this.contentType = contentType;
        this.data = bytes;
    }
}