package com.github.bedrock.util.exception;

public class QRCodeException extends Exception {

    public QRCodeException() {
        super();
    }

    public QRCodeException(String message) {
        super(message);
    }

    public QRCodeException(Throwable e) {
        super(e);
    }

    public QRCodeException(String message, Throwable e) {
        super(message, e);
    }

}
