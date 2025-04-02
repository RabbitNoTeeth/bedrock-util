package com.github.bedrock.util.exception;

public class ByteOperationException extends Exception {

    public ByteOperationException() {
        super();
    }

    public ByteOperationException(String message) {
        super(message);
    }

    public ByteOperationException(Throwable e) {
        super(e);
    }

    public ByteOperationException(String message, Throwable e) {
        super(message, e);
    }

}
