package com.github.rabbitnoteeth.bedrock.util.exception;

public class LogException extends Exception {

    public LogException() {
        super();
    }

    public LogException(String message) {
        super(message);
    }

    public LogException(Throwable e) {
        super(e);
    }

    public LogException(String message, Throwable e) {
        super(message, e);
    }

}
