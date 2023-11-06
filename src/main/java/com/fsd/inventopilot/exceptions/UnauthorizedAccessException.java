package com.fsd.inventopilot.exceptions;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
