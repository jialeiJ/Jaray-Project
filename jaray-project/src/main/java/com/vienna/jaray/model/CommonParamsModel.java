package com.vienna.jaray.model;

import lombok.Data;

/**
 * @Title: CommonParamsModel.java
 * @Description: 表格数据通用模型
 * @author Administrator
 * @date 2019年12月5日上午1:01:39
 */
@Data
public class CommonParamsModel {
	/* 查询内容 */
	private String search;
	/* 分页信息，第几页 */
	private int pageNum = 0;
	/* 分页信息，每页的数量 */
	private int pageSize = 10;
}
