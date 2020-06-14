package com.vienna.jaray.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.ComplaintEntity;
import com.vienna.jaray.mapper.ComplaintMapper;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.ComplaintService;

@Service
public class ComplaintServiceImpl implements ComplaintService {
	
	@Autowired
	private ComplaintMapper complaintMapper;

	/**
	 *	查询投诉信息列表
	 */
	@Override
	public ResponseResult findComplaintList(CommonParamsModel commonParamsModel) {
		
		//设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
		List<ComplaintEntity> complaintEntities = complaintMapper.findComplaintList();
		//取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(complaintEntities);
		return ResponseResult.success().add("complaintEntities", pageInfo);
	}

	@Override
	@Transactional(timeout = 30)
	public ResponseResult addOrUpdateComplaint(ComplaintEntity complaintEntity) {
		ComplaintEntity findResult = complaintMapper.findComplaintByCid(complaintEntity.getCid());
		if(findResult != null){
			complaintMapper.updateComplaint(complaintEntity);
		}else {
			complaintMapper.addComplaint(complaintEntity);
		}
		return ResponseResult.success();
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public ResponseResult findComplaintByCid(String cid) {
        ComplaintEntity complaintEntitiy = complaintMapper.findComplaintByCid(cid);
		return ResponseResult.success().add("complaintEntity", complaintEntitiy);
	}

	@Override
	public ResponseResult delComplaint(String[] cids) {
		int result = complaintMapper.delComplaint(cids);
		return ResponseResult.success().add("result", result);
	}

}
