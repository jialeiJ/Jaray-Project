package com.vienna.jaray.entity.system;

import lombok.Data;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统用户Token实体类
 */
@Data
public class SysUserToken {
    private int id;
    private String userId;
    private String name;
    private String token;
    private String expireTime;
    private String createBy;
    private String createTime;
    private String lastUpdateBy;
    private String lastUpdateTime;
}
