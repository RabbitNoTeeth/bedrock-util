package com.github.bedrock.util.exception;

public class YamlException extends Exception {

    public YamlException() {
        super();
    }

    public YamlException(String message) {
        super(message);
    }

    public YamlException(Throwable e) {
        super(e);
    }

    public YamlException(String message, Throwable e) {
        super(message, e);
    }

}
