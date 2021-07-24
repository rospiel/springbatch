package com.study.studyapplicationbatch.job.config;

import com.study.studyapplicationbatch.job.ScheduleJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    //@Bean
    public JobDetail quartzJobDetail() {
        return JobBuilder.newJob(ScheduleJob.class)
                .storeDurably()
                .build();
    }

    //@Bean
    public Trigger jobTrigger(JobDetail quartzJobDetail) {
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(quartzJobDetail)
                .withSchedule(simpleScheduleBuilder)
                .build();
    }
}
