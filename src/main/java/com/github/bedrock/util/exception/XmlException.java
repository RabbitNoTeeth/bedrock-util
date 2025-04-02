package com.github.bedrock.util.exception;

public class XmlException extends Exception {

    public XmlException() {
        super();
    }

    public XmlException(String message) {
        super(message);
    }

    public XmlException(Throwable e) {
        super(e);
    }

    public XmlException(String message, Throwable e) {
        super(message, e);
    }

}
