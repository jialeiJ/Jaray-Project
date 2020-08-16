package com.vienna.jaray.entity.system;

import lombok.Data;

@Data
public class SysRole {
    private String id;
    private String value;
    private String name;
    private String label;
    private String remark;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
    private int del_flag;
}
