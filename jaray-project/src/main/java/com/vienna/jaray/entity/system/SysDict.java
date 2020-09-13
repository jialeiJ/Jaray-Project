package com.vienna.jaray.entity.system;

import lombok.Data;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统字典实体类
 */
@Data
public class SysDict {
    private String id;
    private String value;
    private String label;
    private String type;
    private String description;
    private int sort;
    private String remarks;
    private String createBy;
    private String createTime;
    private String lastUpdateBy;
    private String lastUpdateTime;
    private int delFlag;
}
