package com.vienna.jaray.entity.complaint;

import lombok.Data;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 投诉实体
 */
@Data
public class Complaint {
	private String cid;
	private String title;
	private String desc;
	private String cFileIds;
}
