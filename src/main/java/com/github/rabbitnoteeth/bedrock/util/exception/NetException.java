package com.github.rabbitnoteeth.bedrock.util.exception;

public class NetException extends Exception {

    public NetException() {
        super();
    }

    public NetException(String message) {
        super(message);
    }

    public NetException(Throwable e) {
        super(e);
    }

    public NetException(String message, Throwable e) {
        super(message, e);
    }

}
