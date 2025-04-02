package com.github.bedrock.util.schedule;

import com.github.bedrock.util.StringUtils;
import com.github.bedrock.util.schedule.exception.SchedulerException;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private final org.quartz.Scheduler scheduler;
    private final List<JobDetail> jobDetails = new ArrayList<>();

    private Scheduler() throws Exception {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        this.scheduler = schedulerFactory.getScheduler();
    }

    public static Scheduler create() throws SchedulerException {
        try {
            return new Scheduler();
        } catch (Throwable e) {
            throw new SchedulerException(e);
        }
    }

    public void addJob(JobDetail jobDetail) {
        this.jobDetails.add(jobDetail);
    }

    public void addJobs(List<JobDetail> jobDetails) {
        this.jobDetails.addAll(jobDetails);
    }

    public void start() throws SchedulerException {
        try {
            scheduleJobs();
            scheduler.start();
        } catch (Throwable e) {
            throw new SchedulerException(e);
        }
    }

    public void startDelayed(int seconds) throws SchedulerException {
        try {
            scheduleJobs();
            scheduler.startDelayed(seconds);
        } catch (Throwable e) {
            throw new SchedulerException(e);
        }
    }

    public boolean isStarted() throws SchedulerException {
        try {
            return scheduler.isStarted();
        } catch (Throwable e) {
            throw new SchedulerException(e);
        }
    }

    public void shutdown() throws SchedulerException {
        try {
            scheduler.shutdown();
        } catch (Throwable e) {
            throw new SchedulerException(e);
        }
    }

    public void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
        try {
            scheduler.shutdown(waitForJobsToComplete);
        } catch (Throwable e) {
            throw new SchedulerException(e);
        }
    }

    public boolean isShutdown() throws SchedulerException {
        try {
            return scheduler.isShutdown();
        } catch (Throwable e) {
            throw new SchedulerException(e);
        }
    }

    private void scheduleJobs() throws SchedulerException {
        try {
            for (JobDetail jobDetail : jobDetails) {
                String jobName = jobDetail.getName();
                String jobGroup = jobDetail.getGroup();
                String jobDescription = jobDetail.getDescription();
                if (StringUtils.isBlank(jobDescription)) {
                    jobDescription = jobName;
                }
                Trigger trigger = jobDetail.getTrigger();
                JobBuilder jobBuilder = JobBuilder.newJob(jobDetail.getJobClass())
                    .withIdentity(jobName, jobGroup)
                    .withDescription(jobDescription);
                JobDataMap data = jobDetail.getData();
                if (data != null) {
                    jobBuilder.usingJobData(data);
                }
                scheduler.scheduleJob(jobBuilder.build(), trigger.bindJob(jobName, jobGroup));
            }
        } catch (Throwable e) {
            throw new SchedulerException(e);
        }
    }


}
