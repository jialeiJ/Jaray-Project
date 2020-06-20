package com.vienna.jaray.controller;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.ComplaintEntity;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.ComplaintService;
import com.vienna.jaray.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ILogAnnotation(value = "查询用户列表")
    @GetMapping("/find/all")
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysUserService.findAll(commonParamsModel);
        } catch (Exception e) {
            log.error("findAll Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "添加用户")
    @PostMapping("/add")
    public ResponseResult add(SysUserEntity sysUserEntity) {
        ResponseResult resultMsg = null;

        try {
            resultMsg = sysUserService.add(sysUserEntity);
        } catch (Exception e) {
            log.error("add Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "删除用户")
    @PostMapping("/delete")
    public ResponseResult deleteByIds(String ids) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysUserService.deleteByIds(ids.split(","));
        } catch (Exception e) {
            log.error("deleteByIds Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "更新用户")
    @PostMapping("/update")
    public ResponseResult updateById(SysUserEntity sysUserEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysUserService.updateById(sysUserEntity);
        } catch (Exception e) {
            log.error("updateById Method Exception", e);
        }
        return resultMsg;
    }


    @ILogAnnotation(value = "查询用户")
    @PostMapping("/find")
    public ResponseResult findById(String id) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysUserService.findById(id);
        } catch (Exception e) {
            log.error("findById Method Exception", e);
        }
        return resultMsg;
    }
}
