package com.vienna.jaray.entity;

import lombok.Data;

@Data
public class SysRolePermEntity {
    private String id;
    private String name;
    private String perm;
    private String type;
    private String icon;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
}
