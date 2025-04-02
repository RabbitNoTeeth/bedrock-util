package com.github.bedrock.util.schedule;

import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.TriggerBuilder;

import java.util.Date;

public class CronTrigger implements Trigger {

    private final String cronExpression;
    private int priority = org.quartz.Trigger.DEFAULT_PRIORITY;
    private Date startTime;
    private Date endTime;

    public CronTrigger(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @Override
    public org.quartz.Trigger bindJob(String jobName, String jobGroup) {
        ScheduleBuilder<org.quartz.CronTrigger> scheduleBuilder = CronScheduleBuilder.cronSchedule(this.cronExpression);
        TriggerBuilder<org.quartz.CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
            .withIdentity("trigger_" + jobName, "trigger_" + jobGroup)
            .withDescription("trigger_" + jobName)
            .withPriority(this.priority)
            .withSchedule(scheduleBuilder);
        Date startTime = this.startTime;
        if (startTime == null) {
            triggerBuilder.startNow();
        } else {
            triggerBuilder.startAt(startTime);
        }
        Date endTime = this.endTime;
        if (endTime != null) {
            triggerBuilder.endAt(endTime);
        }
        return triggerBuilder.build();
    }

    @Override
    public String toString() {
        return "Trigger{" +
            "cronExpression='" + cronExpression + '\'' +
            ", priority=" + priority +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            '}';
    }

}
