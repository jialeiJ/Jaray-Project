package com.vienna.jaray.entity.quartz;

import lombok.Data;

@Data
public class Job {
    private String jobName;
    private String jobGroupName;
    private String jobTime;

    private String className;
}
