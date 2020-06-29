package com.vienna.jaray.model;

import com.vienna.jaray.entity.SysMenuEntity;
import lombok.Data;

import java.util.List;

@Data
public class TreeModel {
    private String id;
    private String label;
    private int parent_id;
    private String url;
    private int type;
    private String icon;
    private int order_num;
    private String create_by;
    private String create_time;
    private String last_update_by;
    private String last_update_time;
    private int del_flag;

    private List<TreeModel> children;

}
