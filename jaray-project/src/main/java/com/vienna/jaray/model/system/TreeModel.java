package com.vienna.jaray.model.system;

import lombok.Data;

import java.util.List;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 树形数据通用模型
 */
@Data
public class TreeModel {
    private String id;
    private String label;
    private int parentId;
    private String url;
    private int type;
    private String icon;
    private int orderNum;
    private String createBy;
    private String createTime;
    private String lastUpdateBy;
    private String lastUpdateTime;
    private int delFlag;

    private List<TreeModel> children;

}
