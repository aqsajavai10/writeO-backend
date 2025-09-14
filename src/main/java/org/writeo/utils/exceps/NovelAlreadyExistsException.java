package org.writeo.utils.exceps;

public class NovelAlreadyExistsException extends RuntimeException {
    public NovelAlreadyExistsException(String message) {
        super(message);
    }
}
