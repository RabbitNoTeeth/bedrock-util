package com.gitee.rabbitnoteeth.bedrock.util.schedule;

public interface Trigger {

    org.quartz.Trigger bindJob(String jobName, String jobGroup);

}
