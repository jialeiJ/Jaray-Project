package com.vienna.jaray.entity;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SysMenuEntity {
	private String id;
	private String name;
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
	
	private List<SysMenuEntity> childMenus;

}
