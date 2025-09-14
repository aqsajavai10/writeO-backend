package org.writeo.utils.exceps;

public class ChapterNotFoundException extends RuntimeException {
    public ChapterNotFoundException(String message) {
        super(message);
    }
}