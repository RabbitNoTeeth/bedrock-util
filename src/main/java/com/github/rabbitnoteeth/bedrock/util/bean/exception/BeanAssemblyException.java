package com.github.rabbitnoteeth.bedrock.util.bean.exception;

public class BeanAssemblyException extends Exception{

    public BeanAssemblyException(String message) {
        super(message);
    }

    public BeanAssemblyException(Throwable e) {
        super(e);
    }

    public BeanAssemblyException(String message, Throwable e) {
        super(message, e);
    }

}
