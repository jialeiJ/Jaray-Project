package com.vienna.jaray.controller.quartz;

import com.vienna.jaray.service.scheduled.QuartzServiceImpl;
import com.vienna.jaray.service.scheduled.TimedTaskFirst;
import com.vienna.jaray.service.scheduled.TimedTaskSecond;
import lombok.extern.slf4j.Slf4j;
import org.quartz.ee.jmx.jboss.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/quartz/task")
public class QuartzController {
    @Autowired
    private QuartzServiceImpl quartzService;

    @PostMapping("/addjob")
    public void startJob(String type) {
        if("TestJob1".equals(type)) {
            quartzService.addJob(TimedTaskFirst.class, "job1", "test", "0/5 * * * * ?");
        }else {
            quartzService.addJob(TimedTaskSecond.class, "job2", "test", "0/5 * * * * ?");
        }
    }

    @PostMapping("/updatejob")
    public void updatejob() {
        quartzService.updateJob("job1", "test", "0/10 * * * * ?");
    }

    @PostMapping("/deletejob")
    public void deletejob() {
        quartzService.deleteJob("job2", "test");
    }

    @PostMapping("/pauseJob")
    public void pauseJob() {
        quartzService.pauseJob("job2", "test");
    }

    @PostMapping("/resumeJob")
    public void resumeJob() {
        quartzService.resumeJob("job1", "test");
    }

    @PostMapping("/queryAllJob")
    public Object queryAllJob() {
        return quartzService.queryAllJob();
    }


    @PostMapping("/queryRunJob")
    public Object queryRunJob() {
        return quartzService.queryRunJob();
    }
}
