package ru.mechaneg.basejava.exception;

public class ResumeSerializationError extends RuntimeException {
    public ResumeSerializationError(String message) {
        super(message);
    }
    public ResumeSerializationError(String message, Exception ex) {
        super(message, ex);
    }
}
