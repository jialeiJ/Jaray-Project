package com.vienna.jaray.controller.quartz;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.quartz.Job;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.scheduled.QuartzServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 定时任务控制器
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/quartz/job")
public class QuartzController {
    @Autowired
    private QuartzServiceImpl quartzService;

    @PostMapping("/addJob")
    public ResponseResult startJob(Job job) throws ClassNotFoundException {
        return quartzService.addJob((Class<? extends QuartzJobBean>) Class.forName(job.getClassName()), job.getJobName(), job.getJobGroupName(), job.getJobTime());
//        if("TestJob1".equals(type)) {
//            return quartzService.addJob(TimedTaskFirst.class, "job1", "test", "0/5 * * * * ?");
//        }else {
//            return quartzService.addJob(TimedTaskSecond.class, "job2", "test", "0/5 * * * * ?");
//        }
    }

    @PostMapping("/updateJob")
    public ResponseResult updatejob(Job job) {
        return quartzService.updateJob(job.getJobName(), job.getJobGroupName(), job.getJobTime());
    }

    @PostMapping("/deleteJob")
    public ResponseResult deletejob(Job job) {
        return quartzService.deleteJob(job.getJobName(), job.getJobGroupName());
    }

    @PostMapping("/pauseJob")
    public ResponseResult pauseJob(Job job) {
        return quartzService.pauseJob(job.getJobName(), job.getJobGroupName());
    }

    @PostMapping("/resumeJob")
    public ResponseResult resumeJob(Job job) {
        return quartzService.resumeJob(job.getJobName(), job.getJobGroupName());
    }

    @PostMapping("/find/all")
    public ResponseResult queryAllJob(CommonParamsModel commonParamsModel) {
        return quartzService.findAllJob(commonParamsModel);
    }


    @PostMapping("/find/run")
    public ResponseResult queryRunJob(CommonParamsModel commonParamsModel) {
        return quartzService.findRunJob(commonParamsModel);
    }
}
