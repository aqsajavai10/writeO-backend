package org.writeo.utils.exceps;


public class CustomNullPointerException extends NullPointerException {
    private static final long serialVersionUID = 1L;
    public CustomNullPointerException(String message) {
        super(message);
    }
    public CustomNullPointerException(String message, Object... args) {
        super(String.format(message, args));
    }

}