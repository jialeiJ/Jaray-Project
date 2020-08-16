package com.vienna.jaray.entity.system;

import lombok.Data;

@Data
public class SysUserRole {
    private String id;
    private String user_id;
    private String role_id;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
}
