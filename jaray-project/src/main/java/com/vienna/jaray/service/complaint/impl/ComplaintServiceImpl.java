package com.vienna.jaray.service.complaint.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class ComplaintServiceImpl implements ComplaintService {
	
	@Autowired
	private ComplaintMapper complaintMapper;

	/**
	 *	查询投诉信息列表
	 */
	@Override
	public ResponseResult findAll(CommonParamsModel commonParamsModel) {
		
		//设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
		List<Complaint> complaintEntities = complaintMapper.findAll();
		//取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(complaintEntities);
		return ResponseResult.success().add("complaints", pageInfo);
	}

	@Override
	@Transactional(timeout = 30)
	public ResponseResult add(Complaint complaintEntity) {
		ResponseResult responseResult = ResponseResult.fail();
		Complaint findResult = complaintMapper.findByCid(complaintEntity.getCid());
		int result = complaintMapper.add(complaintEntity);
		if(result > 0){
			responseResult = ResponseResult.success().add("complaint", complaintEntity);
		}
		return responseResult;
	}

	@Override
	@Transactional(timeout = 30)
	public ResponseResult updateByCid(Complaint complaintEntity) {
		ResponseResult responseResult = ResponseResult.fail();
		Complaint findResult = complaintMapper.findByCid(complaintEntity.getCid());
		int result = complaintMapper.updateByCid(complaintEntity);
		if(result > 0){
			responseResult = ResponseResult.success().add("complaint", complaintEntity);
		}
		return responseResult;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public ResponseResult findByCid(String cid) {
        Complaint complaintEntitiy = complaintMapper.findByCid(cid);
		return ResponseResult.success().add("complaint", complaintEntitiy);
	}

	@Override
	public ResponseResult deleteByCids(String[] cids) {
		int result = complaintMapper.deleteByCids(cids);
		return ResponseResult.success().add("result", result);
	}

}
