package com.fsd.inventopilot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.nio.charset.StandardCharsets;
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

            // Logging for debugging
            System.out.println("File Signature: " + Arrays.toString(signature));

            // Check for PNG file signature
            if (Arrays.equals(signature, new byte[]{(byte) 0x89, 'P', 'N', 'G'})) {
                return true;
            }

            // Check for JPEG file signature
            if (Arrays.equals(signature, new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xE1}) ||
                    Arrays.equals(signature, new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xDB}) ||
                    Arrays.equals(signature, new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xC0}) ||
                    // Add more JPEG markers as needed
                    Arrays.equals(signature, new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, (byte) 0xC4})) {
                return true;
            }

            // Check for BMP file signature
            if (Arrays.equals(signature, new byte[]{(byte) 'B', (byte) 'M'}) ||
                    Arrays.equals(signature, new byte[]{(byte) 'B', (byte) 'A'}) ||
                    Arrays.equals(signature, new byte[]{(byte) 'C', (byte) 'I'}) ||
                    Arrays.equals(signature, new byte[]{(byte) 'C', (byte) 'P'}) ||
                    Arrays.equals(signature, new byte[]{(byte) 'B', (byte) 'M', 54, -47}) ||
                    Arrays.equals(signature, new byte[]{(byte) 'B', (byte) 'A', 54, -47}) ||
                    Arrays.equals(signature, new byte[]{(byte) 'C', (byte) 'I', 54, -47}) ||
                    Arrays.equals(signature, new byte[]{(byte) 'C', (byte) 'P', 54, -47})) {
                return true;
            }

            // Check for SVG file signature
            return new String(signature, StandardCharsets.UTF_8).startsWith("<svg");
        } catch (Exception e) {
            // Handle the exception appropriately
            e.printStackTrace();
            return false;
        }
    }
}