package com.hundsun.jrescloud.demo.rpc.server.common.util;

import java.text.DateFormat;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class QuartzManager {

    private static final Logger logger = LoggerFactory.getLogger(QuartzManager.class);

    private static SchedulerFactory sf = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "default-group";
    private static String TRIGGER_GROUP_NAME = "default-trigger";

    public static void startScheduler(Job job, String jobName, String corn) throws SchedulerException {
        Scheduler sched = sf.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(jobName, JOB_GROUP_NAME).build();

        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME).withSchedule(
                CronScheduleBuilder.cronSchedule(corn)).build();

        sched.start();

        Date ft = sched.scheduleJob(jobDetail, trigger);
        logger.info("Job:" + jobName + " deployed; Cron:\"" + corn + "\"; next execution time : "
                + DateFormat.getDateTimeInstance(2, 2).format(ft));

        // 立即执行任务
        // sched.triggerJob(new JobKey(jobName, JOB_GROUP_NAME));
    }
}
