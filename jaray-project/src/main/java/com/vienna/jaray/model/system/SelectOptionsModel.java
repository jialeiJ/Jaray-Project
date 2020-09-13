package com.vienna.jaray.model.system;

import lombok.Data;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 下拉框数据通用模型
 */
@Data
public class SelectOptionsModel {
	private String value;
	private String name;
	private String label;
}
