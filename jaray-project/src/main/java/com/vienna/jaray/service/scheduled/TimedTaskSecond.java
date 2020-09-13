package com.vienna.jaray.service.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Jaray
 * @date 2020年09月12日 13:58
 * @description: 定时任务二
 */
@Slf4j
@Component
public class TimedTaskSecond extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("{}", new Date() + "    second     job执行     " + Thread.currentThread().getName());
    }
}
