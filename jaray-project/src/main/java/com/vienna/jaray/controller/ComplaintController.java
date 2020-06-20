package com.vienna.jaray.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.ComplaintEntity;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.ComplaintService;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.server.PathParam;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/complaint")
public class ComplaintController {
	
	@Autowired
	private ComplaintService complaintService;
	
	@ILogAnnotation(value = "查询投诉列表")
	@GetMapping("/find/all")
	public ResponseResult findAll(CommonParamsModel commonParamsModel) {
		ResponseResult resultMsg = null;
		try {
			resultMsg = complaintService.findAll(commonParamsModel);
		} catch (Exception e) {
			log.error("findAll Method Exception", e);
		}
		return resultMsg;
	}
	
	@ILogAnnotation(value = "添加投诉信息")
	@PostMapping("/add")
	public ResponseResult add(ComplaintEntity complaintEntity) {
		ResponseResult resultMsg = null;
		try {
			resultMsg = complaintService.add(complaintEntity);
		} catch (Exception e) {
			log.error("add Method Exception", e);
		}
		return resultMsg;
	}

	@ILogAnnotation(value = "删除投诉信息")
	@PostMapping("/delete")
	public ResponseResult deleteByCids(String cids) {
		ResponseResult resultMsg = null;
		try {
			resultMsg = complaintService.deleteByCids(cids.split(","));
		} catch (Exception e) {
			log.error("deleteByCids Method Exception", e);
		}
		return resultMsg;
	}

	@ILogAnnotation(value = "更新投诉信息")
	@PostMapping("/update")
	public ResponseResult updateByCid(ComplaintEntity complaintEntity) {
		ResponseResult resultMsg = null;
		complaintEntity.setCFileIds("11,22");
		try {
			resultMsg = complaintService.updateByCid(complaintEntity);
		} catch (Exception e) {
			log.error("updateByCid Method Exception", e);
		}
		return resultMsg;
	}


	@ILogAnnotation(value = "查询投诉信息")
	@PostMapping("/find")
	public ResponseResult findByCid(String cid) {
		ResponseResult resultMsg = null;
		try {
			resultMsg = complaintService.findByCid(cid);
		} catch (Exception e) {
			log.error("findByCid Method Exception", e);
		}
		return resultMsg;
	}

}
