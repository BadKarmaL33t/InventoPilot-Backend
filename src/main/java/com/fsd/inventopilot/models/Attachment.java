package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false, unique = true)
    private String fileName;
    @Column(nullable = false)
    private String contentType;
    @Column(nullable = false)
    @Lob
    private byte[] data;

    public Attachment(String filename, String contentType, byte[] bytes) {
        this.fileName = filename;
        this.contentType = contentType;
        this.data = bytes;
    }
}
