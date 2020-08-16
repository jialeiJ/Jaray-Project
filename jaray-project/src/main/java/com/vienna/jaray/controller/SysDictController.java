package com.vienna.jaray.controller;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysDict;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.SysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    @ILogAnnotation(value = "查询字典列表")
    @PostMapping("/find/all")
    @PreAuthorize("hasAuthority('sys:dict:view')")
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDictService.findAll(commonParamsModel);
        } catch (Exception e) {
            log.error("findAll Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "添加字典")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:dict:add')")
    public ResponseResult add(SysDict sysDictEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDictService.add(sysDictEntity);
        } catch (Exception e) {
            log.error("add Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "删除字典")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    public ResponseResult deleteByIds(String ids) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDictService.deleteByIds(ids.split(","));
        } catch (Exception e) {
            log.error("deleteByIds Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "更新字典")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:dict:edit')")
    public ResponseResult updateById(SysDict sysDictEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDictService.updateById(sysDictEntity);
        } catch (Exception e) {
            log.error("updateById Method Exception", e);
        }
        return resultMsg;
    }


    @ILogAnnotation(value = "查询字典")
    @PostMapping("/find")
    @PreAuthorize("hasAuthority('sys:dict:view')")
    public ResponseResult findById(String id) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDictService.findById(id);
        } catch (Exception e) {
            log.error("findById Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "查询字典")
    @PostMapping("/find/desc")
    public ResponseResult findByDesc(String description){
        return sysDictService.findByDesc(description);
    }
}
