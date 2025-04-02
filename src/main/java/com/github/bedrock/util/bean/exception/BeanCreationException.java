package com.github.bedrock.util.bean.exception;

public class BeanCreationException extends Exception{

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(Throwable e) {
        super(e);
    }

    public BeanCreationException(String message, Throwable e) {
        super(message, e);
    }

}
