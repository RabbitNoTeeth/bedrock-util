package com.github.rabbitnoteeth.bedrock.util.exception;

public class ExecutionException extends Exception {

    public ExecutionException() {
        super();
    }

    public ExecutionException(String message) {
        super(message);
    }

    public ExecutionException(Throwable e) {
        super(e);
    }

    public ExecutionException(String message, Throwable e) {
        super(message, e);
    }

}
