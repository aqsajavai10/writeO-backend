package org.writeo.exceps;

public class CustomInvalidFormatException extends RuntimeException {
    public CustomInvalidFormatException(String message) {
        super(message);
    }
}