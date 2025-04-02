package com.github.bedrock.util.test.schedule;

import com.github.bedrock.util.DateUtils;
import com.github.bedrock.util.bean.annotation.Bean;
import com.github.bedrock.util.schedule.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean
public class TestJob2 implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestJob2.class);

    public TestJob2() {
        LOGGER.info("TestJob2 创建了");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(DateUtils.now() + ": test job run ...");
    }

    @Override
    public String toString() {
        return "TestJob2";
    }
}
