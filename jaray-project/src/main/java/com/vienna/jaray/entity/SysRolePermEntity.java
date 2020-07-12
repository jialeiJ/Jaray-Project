package com.vienna.jaray.entity;

import lombok.Data;

@Data
public class SysRolePermEntity {
    private String id;
    private String role_id;
    private String perm_id;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
}
