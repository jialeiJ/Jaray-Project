package com.vienna.jaray.service.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class TimedTaskSecond extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("{}", new Date() + "    second     job执行     " + Thread.currentThread().getName());
    }
}
