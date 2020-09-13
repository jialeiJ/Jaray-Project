package com.vienna.jaray.entity.quartz;

import lombok.Data;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 定时任务实体类
 */
@Data
public class Job {
    private String jobName;
    private String jobClass;
    private String jobGroupName;
    private String jobTime;
    private String description;
    private String jobStatus;

    private String className;
}
