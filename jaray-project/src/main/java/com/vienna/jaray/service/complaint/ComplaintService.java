package com.vienna.jaray.service.complaint;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.complaint.Complaint;
import com.vienna.jaray.model.system.CommonParamsModel;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 投诉接口类
 */
public interface ComplaintService {
	/**
	 * 查询投诉信息
	 * @param commonParamsModel 通用参数
	 * @return 投诉信息
	 */
	ResponseResult findAll(CommonParamsModel commonParamsModel);

	/**
	 * 新增投诉信息
	 * @param complaint 投诉实体
	 * @return 新增结果
	 */
	ResponseResult add(Complaint complaint);

	/**
	 * 更新投诉信息
	 * @param complaint 投诉实体
	 * @return 更新结果
	 */
	ResponseResult updateByCid(Complaint complaint);

	/**
	 * 查询投诉信息
	 * @param cid 投诉id
	 * @return 投诉信息
	 */
	ResponseResult findByCid(String cid);

	/**
	 * 删除投诉信息
	 * @param cids cid数组
	 * @return 删除结果
	 */
	ResponseResult deleteByCids(String[] cids);
}
