package com.vienna.jaray.entity.system;

import lombok.Data;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统用户实体类
 */
@Data
public class SysUser {
    private String id;
    private String name;
    private String password;
    private String menuPerm;
    private String email;
    private String mobile;
    private String status;
    private String deptId;
    private String roleId;
    private String createBy;
    private String createTime;
    private String lastUpdateBy;
    private String lastUpdateTime;
    private int delFlag;
}
