package com.vienna.jaray.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @Title: ILog.java
 * @Description: 用户日志记录实体
 * @author Administrator
 * @date 2019年10月13日下午10:11:21
 */
@Data
public class ISysLog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/* 编号id */
	private Long id;
	/* 用户名 */
	private String username;
	/* 操作 */
	private String operation;
	/* 方法名 */
	private String method;
	/* 参数 */
	private String params;
	/* ip地址 */
	private String ip;
	/* 操作时间 */
	private Date createDate;

}
