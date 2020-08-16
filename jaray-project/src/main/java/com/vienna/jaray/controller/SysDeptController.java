package com.vienna.jaray.controller;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysDept;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @ILogAnnotation(value = "查询部门列表")
    @PostMapping("/find/all")
    @PreAuthorize("hasAuthority('sys:dept:view')")
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDeptService.findAllTop(commonParamsModel);
        } catch (Exception e) {
            log.error("findAll Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "添加部门")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:dept:add')")
    public ResponseResult add(SysDept sysDeptEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDeptService.add(sysDeptEntity);
        } catch (Exception e) {
            log.error("add Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "删除部门")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    public ResponseResult deleteByIds(String ids) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDeptService.deleteByIds(ids.split(","));
        } catch (Exception e) {
            log.error("deleteByIds Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "更新部门")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:dept:edit')")
    public ResponseResult updateById(SysDept sysDeptEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDeptService.updateById(sysDeptEntity);
        } catch (Exception e) {
            log.error("updateById Method Exception", e);
        }
        return resultMsg;
    }


    @ILogAnnotation(value = "查询部门")
    @PostMapping("/find")
    @PreAuthorize("hasAuthority('sys:dept:view')")
    public ResponseResult findById(String id) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDeptService.findById(id);
        } catch (Exception e) {
            log.error("findById Method Exception", e);
        }
        return resultMsg;
    }
}
