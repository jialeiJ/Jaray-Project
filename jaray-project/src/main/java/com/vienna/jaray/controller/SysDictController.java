package com.vienna.jaray.controller;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysDictEntity;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.SysDictService;
import com.vienna.jaray.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    @ILogAnnotation(value = "查询字典列表")
    @GetMapping("/find/all")
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
    public ResponseResult add(SysDictEntity sysDictEntity) {
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
    public ResponseResult updateById(SysDictEntity sysDictEntity) {
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
    public ResponseResult findById(String id) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysDictService.findById(id);
        } catch (Exception e) {
            log.error("findById Method Exception", e);
        }
        return resultMsg;
    }
}