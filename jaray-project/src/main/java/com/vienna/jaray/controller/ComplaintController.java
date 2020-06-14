package com.vienna.jaray.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.ComplaintEntity;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.ComplaintService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/complaint")
public class ComplaintController {
	
	@Autowired
	private ComplaintService complaintService;
	
	@ILogAnnotation(value = "查询投诉信息")
	@GetMapping("/findComplaintList")
	public ResponseResult findComplaintList(CommonParamsModel commonParamsModel) {
		ResponseResult resultMsg = null;
		try {
			log.info("------------查询投诉信息列表开始------------");
			resultMsg = complaintService.findComplaintList(commonParamsModel);
			log.info("------------查询投诉信息列表结束------------");
		} catch (Exception e) {
			log.error("findComplaintList Method Exception", e);
		}
		return resultMsg;
	}
	
	@ILogAnnotation(value = "新增或编辑投诉信息")
	@PostMapping("/addOrUpdateComplaint")
	public ResponseResult addOrUpdateComplaint(ComplaintEntity complaintEntity) {
		ResponseResult resultMsg = null;

		complaintEntity.setCFileIds("11,22");
		try {
			log.info("------------新增投诉信息开始------------");
			resultMsg = complaintService.addOrUpdateComplaint(complaintEntity);
			log.info("------------新增投诉信息结束------------");
		} catch (Exception e) {
			log.error("insertComplaint Method Exception", e);
		}
		return resultMsg;
	}

	@ILogAnnotation(value = "删除投诉信息")
	@PostMapping("/delComplaint")
	public ResponseResult delComplaint(String cids) {
		ResponseResult resultMsg = null;
		try {
			log.info("------------删除投诉信息开始------------");
			resultMsg = complaintService.delComplaint(cids.split(","));
			log.info("------------删除投诉信息结束------------");
		} catch (Exception e) {
			log.error("insertComplaint Method Exception", e);
		}
		return resultMsg;
	}


	@ILogAnnotation(value = "查询投诉信息")
	@PostMapping("/findComplaint")
	public ResponseResult findComplaint(String cid) {
		ResponseResult resultMsg = null;
		try {
			log.info("------------查询投诉信息开始------------");
			resultMsg = complaintService.findComplaintByCid(cid);
			log.info("------------查询投诉信息结束------------");
		} catch (Exception e) {
			log.error("insertComplaint Method Exception", e);
		}
		return resultMsg;
	}

}
