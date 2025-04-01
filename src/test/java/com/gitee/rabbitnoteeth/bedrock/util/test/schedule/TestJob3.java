package com.gitee.rabbitnoteeth.bedrock.util.test.schedule;

import com.gitee.rabbitnoteeth.bedrock.util.DateUtils;
import com.gitee.rabbitnoteeth.bedrock.util.bean.annotation.Bean;
import com.gitee.rabbitnoteeth.bedrock.util.schedule.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJob3 implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestJob3.class);

    public TestJob3() {
        LOGGER.info("TestJob3 创建了");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(DateUtils.now() + ": test job run ...");
    }

    @Override
    public String toString() {
        return "TestJob3";
    }
}
