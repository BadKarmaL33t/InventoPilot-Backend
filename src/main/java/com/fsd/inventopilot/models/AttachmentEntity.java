package com.fsd.inventopilot.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class AttachmentEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    @JoinColumn(name = "attachment_id", nullable = false)
    private Attachment attachment;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_name")
    private Product product;
}