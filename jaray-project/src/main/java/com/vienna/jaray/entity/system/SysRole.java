package com.vienna.jaray.entity.system;

import lombok.Data;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统角色实体类
 */
@Data
public class SysRole {
    private String id;
    private String value;
    private String name;
    private String label;
    private String remark;
    private String createBy;
    private String createTime;
    private String lastUpdateBy;
    private String lastUpdateTime;
    private int delFlag;
}
