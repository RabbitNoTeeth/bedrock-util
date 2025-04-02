package com.github.rabbitnoteeth.bedrock.util.exception;

public class HttpException extends Exception {

    public HttpException() {
        super();
    }

    public HttpException(String message) {
        super(message);
    }

    public HttpException(Throwable e) {
        super(e);
    }

    public HttpException(String message, Throwable e) {
        super(message, e);
    }

}
