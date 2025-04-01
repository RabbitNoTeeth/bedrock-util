package com.gitee.rabbitnoteeth.bedrock.util.test.schedule;

import com.gitee.rabbitnoteeth.bedrock.util.DateUtils;
import com.gitee.rabbitnoteeth.bedrock.util.bean.annotation.Autowired;
import com.gitee.rabbitnoteeth.bedrock.util.bean.annotation.Bean;
import com.gitee.rabbitnoteeth.bedrock.util.schedule.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean
public class TestJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestJob.class);

    @Autowired
    private TestJob2 testJob2;

    public TestJob(TestJob3 testJob3) {
        LOGGER.info("TestJob1 创建了");
        LOGGER.info("" + testJob3);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(DateUtils.now() + ": test job run ...");
    }

}
