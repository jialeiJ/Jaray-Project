package com.vienna.jaray.entity.system;

import lombok.Data;

@Data
public class SysLog {
    private String id;
    private String user_name;
    private String operation;
    private String method;
    private String params;
    private String time;
    private String ip;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
}