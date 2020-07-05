package com.vienna.jaray.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserEntity {
    private String id;
    private String name;
    private String password;
    private String menu_perm;
    private String email;
    private String mobile;
    private int status;
    private int dept_id;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
    private int del_flag;
}
