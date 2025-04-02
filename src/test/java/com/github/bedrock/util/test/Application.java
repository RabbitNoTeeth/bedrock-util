package com.github.bedrock.util.test;

import com.github.bedrock.util.ExecutorUtils;
import com.github.bedrock.util.LogUtils;
import com.github.bedrock.util.bean.BeanContainer;
import com.github.bedrock.util.schedule.IntervalTrigger;
import com.github.bedrock.util.schedule.Job;
import com.github.bedrock.util.schedule.JobDetail;
import com.github.bedrock.util.schedule.Scheduler;
import com.github.bedrock.util.test.schedule.TestJob;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Application {

    public static void main(String[] args) throws Exception {
//        testSchedule();
//        testExecutor();
//        testLogback();
//        testBeanUtils();
//        testClass();

        testValidate();
    }

    private static void testValidate() {
        Integer a = 1;
        System.out.println(a instanceof Integer);
    }

    private static void testLogback() throws Exception {
        LogUtils.loadConfiguration(Application.class.getClassLoader().getResourceAsStream("logback-dev.xml"));
    }

    private static void testClass() {
        Class<?> class_ = TestJob.class;
        while (class_ != null) {
            System.out.println(class_);
            class_ = class_.getSuperclass();
        }
        System.out.println(Object.class.getSuperclass());
    }

    private static void testBeanUtils() throws Exception {
        BeanContainer.scan("com.gitee");
        List<Job> jobs = BeanContainer.getBeansByType(Job.class);
        jobs.forEach(job -> System.out.println(job.getClass()));
    }

    private static void testExecutor() throws Exception {
        ExecutorService executorService = ExecutorUtils.createVirtualThreadExecutor("virtual-");
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }
        Thread.sleep(10000000);
    }

    private static void testSchedule() throws Exception {
//        Trigger trigger = new CronTrigger("0/3 * * * * ?");
        IntervalTrigger trigger = new IntervalTrigger(3, IntervalTrigger.TimeUnit.SECOND);
        trigger.setCount(3);
        JobDetail jobDetail = new JobDetail("testJob", "test", trigger, TestJob.class);
        Scheduler scheduler = Scheduler.create();
        scheduler.addJob(jobDetail);
        scheduler.start();
    }

}
