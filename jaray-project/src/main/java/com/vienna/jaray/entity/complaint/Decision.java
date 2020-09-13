package com.vienna.jaray.entity.complaint;

import lombok.Data;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 决策实体
 */
@Data
public class Decision {
	private int did;
	private String dOpinion;
	private String closedLoop;
	private String dFileIds;

}
