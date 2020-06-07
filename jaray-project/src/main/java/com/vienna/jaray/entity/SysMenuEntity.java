package com.vienna.jaray.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SysMenuEntity {
	private int id;
	private String name;
	private int parentId;
	private String url;
	private String perms;
	private int type;
	private String icon;
	private int orderNum;
	private String createBy;
	private Date createTime;
	private String lastUpdateBy;
	private Date lastUpdateTime;
	private int delFlag;
	
	private List<SysMenuEntity> childMenus;

}
