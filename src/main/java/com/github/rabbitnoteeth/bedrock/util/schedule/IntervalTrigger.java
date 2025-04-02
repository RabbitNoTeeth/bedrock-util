package com.github.rabbitnoteeth.bedrock.util.schedule;

import org.quartz.*;

public class IntervalTrigger implements Trigger {

    private final int interval;
    private final TimeUnit timeUnit;
    private int count = 0;

    public IntervalTrigger(int interval, TimeUnit timeUnit) {
        this.interval = interval;
        this.timeUnit = timeUnit;
    }

    public int getInterval() {
        return interval;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public org.quartz.Trigger bindJob(String jobName, String jobGroup) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        switch (this.timeUnit) {
            case SECOND -> scheduleBuilder.withIntervalInSeconds(this.interval);
            case MINUTE -> scheduleBuilder.withIntervalInMinutes(this.interval);
            case HOUR -> scheduleBuilder.withIntervalInHours(this.interval);
            case null, default -> scheduleBuilder.withIntervalInMilliseconds(this.interval);
        }
        if (this.count < 1) {
            scheduleBuilder.repeatForever();
        } else {
            scheduleBuilder.withRepeatCount(this.count - 1);
        }
        TriggerBuilder<SimpleTrigger> triggerBuilder = TriggerBuilder.newTrigger()
            .withIdentity("trigger_" + jobName, "trigger_" + jobGroup)
            .withDescription("trigger_" + jobName)
            .withSchedule(scheduleBuilder)
            .startNow();
        return triggerBuilder.build();
    }

    @Override
    public String toString() {
        return "IntervalTrigger{" +
            "interval=" + interval +
            ", timeUnit=" + timeUnit +
            ", count=" + count +
            '}';
    }

    public static enum TimeUnit {
        MILLISECOND,
        SECOND,
        MINUTE,
        HOUR
    }
}
