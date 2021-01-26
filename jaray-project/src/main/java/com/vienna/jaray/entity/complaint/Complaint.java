package com.vienna.jaray.entity.complaint;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 投诉实体
 */
@Data
public class Complaint implements Serializable {
	private static final long serialVersionUID = 4666538349868042604L;
	private String cid;
	private String title;
	private String desc;
	private String cFileIds;
}
