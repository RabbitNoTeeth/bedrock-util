package com.gitee.rabbitnoteeth.bedrock.util.exception;

public class CryptoException extends Exception {

    public CryptoException() {
        super();
    }

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(Throwable e) {
        super(e);
    }

    public CryptoException(String message, Throwable e) {
        super(message, e);
    }

}
