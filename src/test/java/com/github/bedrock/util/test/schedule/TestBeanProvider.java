package com.github.bedrock.util.test.schedule;

import com.github.bedrock.util.bean.annotation.Bean;
import com.github.bedrock.util.bean.annotation.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@BeanProvider
public class TestBeanProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestBeanProvider.class);

    @Bean
    public TestJob3 testJob3(TestJob2 testJob2) {
        LOGGER.info("" + testJob2);
        return new TestJob3();
    }

}
