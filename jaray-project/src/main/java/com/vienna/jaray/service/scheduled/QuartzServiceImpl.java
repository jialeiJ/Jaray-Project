package com.vienna.jaray.service.scheduled;

import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.model.system.CommonParamsModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jaray
 * @date 2020年09月12日 13:58
 * @description: 定时任务实现类
 */
@Slf4j
@Service
public class QuartzServiceImpl {
    @Autowired
    private Scheduler scheduler;

    /**
     * 增加一个job
     *
     * @param jobClass 任务实现类
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @param jobTime 时间表达式 (这是每隔多少秒为一次任务)
     * @param jobTimes 运行的次数 （<0:表示不限次数）
     * @return 增加结果
     */
    public ResponseResult addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime,
                                 int jobTimes) {
        try {
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)
                    .build();

            // 使用simpleTrigger规则
            Trigger trigger;
            if (jobTimes < 0) {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime))
                        .startNow().build();
            } else {
                trigger = TriggerBuilder
                        .newTrigger().withIdentity(jobName, jobGroupName).withSchedule(SimpleScheduleBuilder
                                .repeatSecondlyForever(1).withIntervalInSeconds(jobTime).withRepeatCount(jobTimes))
                        .startNow().build();
            }
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("addJob Method Error", e);
        }
        return ResponseResult.success();
    }

    /**
     * 增加一个job
     *
     * @param jobClass 任务实现类
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @param jobTime 时间表达式 （如：0/5 * * * * ? ）
     * @return 增加结果
     */
    public ResponseResult addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime) {
        try {
            // 创建jobDetail实例，绑定Job实现类,指明job的名称，所在组的名称，以及绑定job类,任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)
                    .build();

            // 使用cornTrigger规则,定义调度触发规则,触发器key
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).startNow().build();

            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error("addJob Method Error", e);
        }
        return ResponseResult.success();
    }

    /**
     * 修改 一个job的 时间表达式
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @param jobTime 时间表达式 （如：0/5 * * * * ? ）
     * @return 修改结果
     */
    public ResponseResult updateJob(String jobName, String jobGroupName, String jobTime) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("updateJob Method Error", e);
        }
        return ResponseResult.success();
    }

    /**
     * 删除任务一个job
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @return 删除结果
     */
    public ResponseResult deleteJob(String jobName, String jobGroupName) {
        try {
            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
        } catch (Exception e) {
            log.error("deleteJob Method Error", e);
        }
        return ResponseResult.success();
    }

    /**
     * 暂停一个job
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @return 暂停结果
     */
    public ResponseResult pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("pauseJob Method Error", e);
        }
        return ResponseResult.success();
    }

    /**
     * 恢复一个job
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @return 恢复结果
     */
    public ResponseResult resumeJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("resumeJob Method Error", e);
        }
        return ResponseResult.success();
    }

    /**
     * 暂停所有job
     *
     * @return 暂停结果
     */
    public ResponseResult pauseAllJob() {
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            log.error("pauseAllJob Method Error", e);
        }
        return ResponseResult.success();
    }

    /**
     * 恢复所有job
     *
     * @return 恢复结果
     */
    public ResponseResult resumeAllJob() {
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {
            log.error("resumeAllJob Method Error", e);
        }
        return ResponseResult.success();
    }

    /**
     * 立即执行一个job
     *
     * @param jobName 任务名称
     * @param jobGroupName 任务组名
     * @return 立即执行结果
     */
    public ResponseResult runAjobNow(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error("runAjobNow Method Error", e);
        }
        return ResponseResult.success();
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return 所有计划中的任务列表
     */
    public ResponseResult findAllJob(CommonParamsModel commonParamsModel) {
        List<Map<String, Object>> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<>(16);
            Map<String, Object> map;
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    map = new HashMap<>(16);
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    packageJobMapData(jobList, map, jobKey, trigger, jobDetail);
                }
            }

        } catch (SchedulerException e) {
            log.error("findAllJob Method Error", e);
        }

        return getResponseResult(commonParamsModel, jobList);
    }

    private ResponseResult getResponseResult(CommonParamsModel commonParamsModel, List<Map<String, Object>> jobList) {
        int pages, total;
        if(CollectionUtils.isNotEmpty(jobList)){
            pages = jobList.size()/commonParamsModel.getPageSize();
            total = jobList.size();
            jobList = jobList.stream().skip((commonParamsModel.getPageNum()-1) * commonParamsModel.getPageSize())
                    .limit(commonParamsModel.getPageSize()).collect(Collectors.toList());

            //取记录总条数
            PageInfo<?> pageInfo = new PageInfo<>(jobList);

            // setPageNum：当前页码
            pageInfo.setPageNum(commonParamsModel.getPageNum());

            // setPageSize： 每页显示数据条数
            pageInfo.setPageSize(commonParamsModel.getPageSize());

            // setPages：设置总页数
            pageInfo.setPages(pages);

            // setTotal：设置总条数
            pageInfo.setTotal(total);
            return ResponseResult.success().add("jobList", pageInfo);
        }
        return ResponseResult.success();
    }

    private void packageJobMapData(List<Map<String, Object>> jobList, Map<String, Object> map, JobKey jobKey, Trigger trigger, JobDetail jobDetail) throws SchedulerException {
        map.put("jobClass", jobDetail.getJobClass());
        map.put("jobName", jobKey.getName());
        map.put("jobGroupName", jobKey.getGroup());
        map.put("description", "触发器:" + trigger.getKey());
        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        map.put("jobStatus", triggerState.name());
        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            String cronExpression = cronTrigger.getCronExpression();
            map.put("jobTime", cronExpression);
        }
        jobList.add(map);
    }

    /**
     * 获取所有正在运行的job
     *
     * @return 所有正在运行的job
     */
    public ResponseResult findRunJob(CommonParamsModel commonParamsModel) {
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap<>(16);
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                packageJobMapData(jobList, map, jobKey, trigger, jobDetail);
            }
        } catch (SchedulerException e) {
            log.error("findRunJob Method Error", e);
        }

        return getResponseResult(commonParamsModel, jobList);
    }
}
