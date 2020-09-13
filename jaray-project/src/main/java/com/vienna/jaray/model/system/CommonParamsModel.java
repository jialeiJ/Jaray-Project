package com.vienna.jaray.model.system;

import lombok.Data;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 表格数据通用模型
 */
@Data
public class CommonParamsModel {
	/** 查询内容 */
	private String search;
	/** 分页信息，第几页 */
	private int pageNum = 0;
	/** 分页信息，每页的数量 */
	private int pageSize = 10;
}
