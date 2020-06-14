package com.vienna.jaray.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysUserTokenEntity {
    private int id;
    private int user_id;
    private String token;
    private Date expire_time;
    private String create_by;
    private Date create_time;
    private String last_update_by;
    private Date last_update_time;
}
