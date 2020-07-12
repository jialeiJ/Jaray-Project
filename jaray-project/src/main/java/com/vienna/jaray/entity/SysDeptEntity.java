package com.vienna.jaray.entity;

import lombok.Data;

@Data
public class SysDeptEntity {
    private String id;
    private String value;
    private String name;
    private String label;
    private String parent_id;
    private int order_num;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
    private int del_flag;
}
