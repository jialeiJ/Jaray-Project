package com.vienna.jaray.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserToken {
    private int id;
    private String user_id;
    private String name;
    private String token;
    private String expire_time;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
}
