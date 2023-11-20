package com.fsd.inventopilot.exceptions;

public class AttachmentStorageException extends Exception {
    public AttachmentStorageException() {
    }

    public AttachmentStorageException(String message) {
        super(message);
    }

    public AttachmentStorageException(String message, Exception e) {
        super(message, e);
    }
}
