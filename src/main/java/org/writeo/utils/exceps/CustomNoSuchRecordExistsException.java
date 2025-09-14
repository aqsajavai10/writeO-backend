package org.writeo.utils.exceps;

public class CustomNoSuchRecordExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public CustomNoSuchRecordExistsException(String message) {
        super(message);
    }
    public CustomNoSuchRecordExistsException(String message, Object... args) {
        super(String.format(message, args));
    }
}