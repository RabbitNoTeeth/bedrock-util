package com.github.bedrock.util.schedule.exception;

public class SchedulerException extends Exception{

    public SchedulerException(String message) {
        super(message);
    }

    public SchedulerException(Throwable e) {
        super(e);
    }

    public SchedulerException(String message, Throwable e) {
        super(message, e);
    }

}
