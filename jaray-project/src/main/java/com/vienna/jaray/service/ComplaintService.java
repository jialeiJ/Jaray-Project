package com.vienna.jaray.service;

import java.util.concurrent.ExecutionException;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.ComplaintEntity;
import com.vienna.jaray.model.CommonParamsModel;

public interface ComplaintService {
	/**
	 * 查询投诉信息
	 * @param commonParamsModel 通用参数
	 * @return
	 */
	public ResponseResult findAll(CommonParamsModel commonParamsModel);

	/**
	 * 新增投诉信息
	 * @param complaintEntity 投诉实体
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ResponseResult add(ComplaintEntity complaintEntity);

	/**
	 * 新增投诉信息
	 * @param complaintEntity 投诉实体
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public ResponseResult updateByCid(ComplaintEntity complaintEntity);

	/**
	 * 查询投诉信息
	 * @param cid 投诉id
	 * @return
	 */
	public ResponseResult findByCid(String cid);

	/**
	 * 删除投诉信息
	 * @param cids cid数组
	 * @return
	 */
	public ResponseResult deleteByCids(String[] cids);

}
