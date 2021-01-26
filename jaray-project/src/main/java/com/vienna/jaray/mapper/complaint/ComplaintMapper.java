package com.vienna.jaray.mapper.complaint;

import java.util.List;

import com.vienna.jaray.model.system.CommonParamsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.vienna.jaray.entity.complaint.Complaint;
import org.springframework.stereotype.Repository;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 投诉Mapper
 */
@Mapper
@Repository
public interface ComplaintMapper {
	/**
	 * 查询投诉列表信息
	 * @return 投诉列表信息
	 */
	List<Complaint> findAll(@Param("common")CommonParamsModel commonParamsModel);

	/**
	 * 添加投诉信息
	 * @param complaint 投诉对象
	 * @return 添加结果
	 */
	int add(@Param("entity") Complaint complaint);

	/**
	 * 查询投诉
	 * @param cid cid
	 * @return 投诉信息
	 */
	Complaint findByCid(@Param("cid")String cid);

	/**
	 * 删除投诉信息
	 * @param cids cid数组
	 * @return 删除结果
	 */
	int deleteByCids(@Param("cids") String[] cids);

	/**
	 * 更新投诉信息
	 * @param complaint 投诉对象
	 * @return 更新结果
	 */
	int updateByCid(@Param("entity") Complaint complaint);
}
