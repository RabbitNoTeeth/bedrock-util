package com.github.bedrock.util.exception;

public class FileException extends Exception {

    public FileException() {
        super();
    }

    public FileException(String message) {
        super(message);
    }

    public FileException(Throwable e) {
        super(e);
    }

    public FileException(String message, Throwable e) {
        super(message, e);
    }

}
