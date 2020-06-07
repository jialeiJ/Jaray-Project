package com.vienna.jaray.service;

import java.util.concurrent.ExecutionException;

import com.vienna.jaray.common.ResponseResultMsg;
import com.vienna.jaray.entity.ComplaintEntity;
import com.vienna.jaray.model.CommonParamsModel;

public interface ComplaintService {
	/**
	 * 查询投诉信息
	 * @param commonParamsModel 通用参数
	 * @return
	 */
	public ResponseResultMsg findComplaintList(CommonParamsModel commonParamsModel);

	/**
	 * 新增投诉信息
	 * @param complaintEntity 投诉实体
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ResponseResultMsg addOrUpdateComplaint(ComplaintEntity complaintEntity);

	/**
	 * 查询投诉信息
	 * @param cid 投诉id
	 * @return
	 */
	public ResponseResultMsg findComplaintByCid(String cid);

	/**
	 * 删除投诉信息
	 * @param cids cid数组
	 * @return
	 */
	public ResponseResultMsg delComplaint(String[] cids);

}
