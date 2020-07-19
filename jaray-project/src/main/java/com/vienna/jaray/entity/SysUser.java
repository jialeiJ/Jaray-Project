package com.vienna.jaray.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class SysUser {
    private String id;
    private String name;
    private String password;
    private String menu_perm;
    private String email;
    private String mobile;
    private String status;
    private String dept_id;
    private String role_id;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
    private int del_flag;
}
