package com.fsd.inventopilot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Arrays;
import java.util.UUID;

@Data
public class AttachmentInputDto {
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private UUID id;
    @NotBlank
    @Pattern(regexp = "^[^';<>&|/\\\\]*$")
    private String fileName;
    @NotBlank
    private String contentType;

    private byte[] data;

    public AttachmentInputDto(String filename, String contentType, byte[] bytes) {
        this.fileName = filename;
        this.contentType = contentType;
        this.data = bytes;
    }

    public boolean isImage() {
        try {
            byte[] signature = Arrays.copyOfRange(data, 0, 4);

            // Check for PNG file signature
            if (Arrays.equals(signature, new byte[]{(byte) 0x89, 'P', 'N', 'G'})) {
                return true;
            }

            // Check for JPEG file signature
            else if (Arrays.equals(signature, new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE0})) {
                return true;
            }

            // Check for BMP file signature
            else return Arrays.equals(signature, new byte[]{'B', 'M'});
        } catch (Exception e) {
            // Handle the exception appropriately
            return false;
        }
    }
}