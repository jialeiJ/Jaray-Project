package com.vienna.jaray.controller.system;

import com.vienna.jaray.annotation.LogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysDept;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.system.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统部门制器
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @LogAnnotation(value = "查询部门列表")
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

    @LogAnnotation(value = "添加部门")
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

    @LogAnnotation(value = "删除部门")
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

    @LogAnnotation(value = "更新部门")
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


    @LogAnnotation(value = "查询部门")
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
