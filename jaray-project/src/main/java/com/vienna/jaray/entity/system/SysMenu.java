package com.vienna.jaray.entity.system;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SysMenu {
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

	private List<SysMenu> perms;
	private List<SysMenu> children = new ArrayList<>();

}
