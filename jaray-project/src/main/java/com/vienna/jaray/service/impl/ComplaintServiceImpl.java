package com.vienna.jaray.service.impl;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vienna.jaray.common.ResponseResultMsg;
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
	public ResponseResultMsg findComplaintList(CommonParamsModel commonParamsModel) {
		
		//设置分页信息(第几页，每页数量)
        PageHelper.startPage(commonParamsModel.getPageNum(), commonParamsModel.getPageSize());
		List<ComplaintEntity> complaintEntities = complaintMapper.findComplaintList();
		//取记录总条数
        PageInfo<?> pageInfo = new PageInfo<>(complaintEntities);
		return ResponseResultMsg.success().add("complaintEntities", pageInfo);
	}

	@Override
	@Transactional(timeout = 30)
	public ResponseResultMsg addOrUpdateComplaint(ComplaintEntity complaintEntity) {
		ComplaintEntity findResult = complaintMapper.findComplaintByCid(complaintEntity.getCid());
		if(findResult != null){
			complaintMapper.updateComplaint(complaintEntity);
		}else {
			complaintMapper.addComplaint(complaintEntity);
		}
		return ResponseResultMsg.success();
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public ResponseResultMsg findComplaintByCid(String cid) {
        ComplaintEntity complaintEntitiy = complaintMapper.findComplaintByCid(cid);
		return ResponseResultMsg.success().add("complaintEntity", complaintEntitiy);
	}

	@Override
	public ResponseResultMsg delComplaint(String[] cids) {
		int result = complaintMapper.delComplaint(cids);
		return ResponseResultMsg.success().add("result", result);
	}

}
