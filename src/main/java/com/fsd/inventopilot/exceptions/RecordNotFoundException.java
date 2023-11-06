package com.fsd.inventopilot.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException() {
        super();
    }
    public RecordNotFoundException(String message) {
        super(message);
    }
}
