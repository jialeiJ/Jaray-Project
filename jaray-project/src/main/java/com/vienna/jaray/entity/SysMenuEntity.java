package com.vienna.jaray.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SysMenuEntity {
	private String id;
	private String value;
	private String name;
	private String label;
	private String parent_id;
	private String url;
	private String perm;
	private int type;
	private String icon;
	private int order_num;
	private String create_by;
	private String create_time;
	private String last_update_by;
	private String last_update_time;
	private int del_flag;

	private List<SysMenuEntity> perms;
	private List<SysMenuEntity> children = new ArrayList<>();

}
