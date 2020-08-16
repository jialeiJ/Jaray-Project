package com.vienna.jaray.entity.system;

import lombok.Data;

@Data
public class SysDict {
    private String id;
    private String value;
    private String label;
    private String type;
    private String description;
    private int sort;
    private String remarks;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
    private int del_flag;
}
