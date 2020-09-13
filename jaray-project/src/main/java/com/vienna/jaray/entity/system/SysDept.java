package com.vienna.jaray.entity.system;

import lombok.Data;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统部门实体类
 */
@Data
public class SysDept {
    private String id;
    private String value;
    private String name;
    private String label;
    private String parentId;
    private int orderNum;
    private String createBy;
    private String createTime;
    private String lastUpdateBy;
    private String lastUpdateTime;
    private int delFlag;

    private List<SysDept> children;
}
