package com.fsd.inventopilot.services.impl;

import com.fsd.inventopilot.dtos.AttachmentDto;
import com.fsd.inventopilot.dtos.AttachmentInputDto;
import com.fsd.inventopilot.exceptions.AttachmentStorageException;
import com.fsd.inventopilot.mappers.AttachmentDtoMapper;
import com.fsd.inventopilot.models.Attachment;
import com.fsd.inventopilot.models.Product;
import com.fsd.inventopilot.models.User;
import com.fsd.inventopilot.repositories.AttachmentRepository;
import com.fsd.inventopilot.repositories.ProductRepository;
import com.fsd.inventopilot.repositories.UserRepository;
import com.fsd.inventopilot.services.AttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentDtoMapper attachmentDtoMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentDtoMapper attachmentDtoMapper, UserRepository userRepository, ProductRepository productRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentDtoMapper = attachmentDtoMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public AttachmentDto addImageToEntity(String entity, String identifier, AttachmentInputDto inputDto) throws AttachmentStorageException {
        if (Objects.equals(entity, "users")) {
            return addImageToUser(identifier, inputDto);
        } else if (Objects.equals(entity, "products")) {
            return addImageToProduct(identifier, inputDto);
        } else {
            throw new AttachmentStorageException("Invalid entity type: " + entity);
        }
    }

    private AttachmentDto addImageToUser(String username, AttachmentInputDto inputDto) throws AttachmentStorageException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Check if the file is an image
            if (!inputDto.isImage()) {
                // Handle the case where a non-image file is detected
                throw new AttachmentStorageException("Only image files are allowed!");
            }
            return processAttachment(inputDto);
        } else {
            throw new AttachmentStorageException("User not found with username " + username);
        }
    }

    private AttachmentDto addImageToProduct(String productName, AttachmentInputDto inputDto) throws AttachmentStorageException {
        Optional<Product> productOptional = productRepository.findByName(productName);
        if (productOptional.isPresent()) {
            // Check if the file is an image
            if (!inputDto.isImage()) {
                // Handle the case where a non-image file is detected
                throw new AttachmentStorageException("Only image files are allowed!");
            }
            return processAttachment(inputDto);
        } else {
            throw new AttachmentStorageException("Product not found with name " + productName);
        }
    }

    private AttachmentDto processAttachment(AttachmentInputDto inputDto) throws AttachmentStorageException {
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
                .orElseThrow(() -> new AttachmentStorageException("Attachment not found with id " + id));
    }
}
