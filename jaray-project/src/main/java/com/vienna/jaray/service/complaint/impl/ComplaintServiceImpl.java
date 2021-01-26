package com.vienna.jaray.service.complaint.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.complaint.Complaint;
import com.vienna.jaray.mapper.complaint.ComplaintMapper;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.complaint.ComplaintService;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 投诉接口实现类
 */
@Service
@CacheConfig(cacheNames = "complaint", cacheManager = "cacheManager")
public class ComplaintServiceImpl implements ComplaintService {
	@Autowired
	private ComplaintMapper complaintMapper;

	/**
	 *	查询投诉信息列表
	 */
	@Override
	//@Cacheable
	public ResponseResult findAll(CommonParamsModel commonParamsModel) {
		//设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
		List<Complaint> complaintEntities = complaintMapper.findAll(commonParamsModel);
		//取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(complaintEntities);
		return ResponseResult.success().add("complaints", pageInfo);
	}

	@Override
	@CachePut(key = "#cid")
	@Transactional(timeout = 30, rollbackFor = Exception.class)
	public ResponseResult add(Complaint complaint) {
		ResponseResult responseResult = ResponseResult.fail();
		Complaint findResult = complaintMapper.findByCid(complaint.getCid());
		int result = complaintMapper.add(complaint);
		if(result > 0){
			responseResult = ResponseResult.success().add("complaint", complaint);
		}
		return responseResult;
	}

	@Override
	@CachePut(key = "#cid")
	@Transactional(timeout = 30, rollbackFor=Exception.class)
	public ResponseResult updateByCid(Complaint complaint) {
		ResponseResult responseResult = ResponseResult.fail();
		Complaint findResult = complaintMapper.findByCid(complaint.getCid());
		int result = complaintMapper.updateByCid(complaint);
		if(result > 0){
			responseResult = ResponseResult.success().add("complaint", complaint);
		}
		return responseResult;
	}

	@Override
	@Cacheable
	@Transactional(timeout = 30, isolation = Isolation.READ_UNCOMMITTED, rollbackFor=Exception.class)
	public ResponseResult findByCid(String cid) {
        Complaint complaintEntitiy = complaintMapper.findByCid(cid);
		return ResponseResult.success().add("complaint", complaintEntitiy);
	}

	@Override
	@CacheEvict(key = "#result.cid")
	public ResponseResult deleteByCids(String[] cids) {
		int result = complaintMapper.deleteByCids(cids);
		return ResponseResult.success().add("result", result);
	}

}
