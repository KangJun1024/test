package com.kangjun.springframework.cronjobs.quartz;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail teatQuartzDetail(){
        return JobBuilder.newJob(TestQuartz.class).withIdentity("testQuartz").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger(){
        return TriggerBuilder.newTrigger().forJob(teatQuartzDetail())
                .withIdentity("testQuartz")
                .startAt(DateUtils.addMinutes(new Date(),-15))
                .build();
    }

    @Bean
    public Trigger testQuartzTrigger1(){
        return TriggerBuilder.newTrigger().forJob(teatQuartzDetail())
                .withIdentity("testQuartz")
                .startAt(DateUtils.addHours(new Date(),-1))
                .build();
    }

    @Bean
    public Trigger testQuartzTrigger2(){
        return TriggerBuilder.newTrigger().forJob(teatQuartzDetail())
                .withIdentity("testQuartz")
                .startAt(DateUtils.addHours(new Date(),-2))
                .build();
    }
}