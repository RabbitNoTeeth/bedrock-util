package com.github.bedrock.util.schedule;

import org.quartz.JobDataMap;

public class JobDetail {

    private final String name;
    private final String group;
    private final Trigger trigger;
    private final Class<? extends org.quartz.Job> jobClass;
    private String description;
    private JobDataMap data;

    public JobDetail(String name, String group, Trigger trigger, Class<? extends org.quartz.Job> jobClass) {
        this.name = name;
        this.group = group;
        this.trigger = trigger;
        this.jobClass = jobClass;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public Class<? extends org.quartz.Job> getJobClass() {
        return jobClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobDataMap getData() {
        return data;
    }

    public void setData(JobDataMap data) {
        this.data = data;
    }
}
