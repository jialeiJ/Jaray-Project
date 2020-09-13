package com.vienna.jaray.controller.system;

import com.vienna.jaray.annotation.LogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysUser;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统用户控制器
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @LogAnnotation(value = "查询用户列表")
    @PostMapping("/find/all")
    @PreAuthorize("hasAuthority('sys:user:view')")
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysUserService.findAll(commonParamsModel);
        } catch (Exception e) {
            log.error("findAll Method Exception", e);
        }
        return resultMsg;
    }

    @LogAnnotation(value = "添加用户")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public ResponseResult add(SysUser sysUserEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysUserService.add(sysUserEntity);
        } catch (Exception e) {
            log.error("add Method Exception", e);
        }
        return resultMsg;
    }

    @LogAnnotation(value = "删除用户")
    @PostMapping("/deleteByIds")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public ResponseResult deleteByIds(String ids) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysUserService.deleteByIds(ids.split(","));
        } catch (Exception e) {
            log.error("deleteByIds Method Exception", e);
        }
        return resultMsg;
    }

    @LogAnnotation(value = "更新用户")
    @PostMapping("/updateById")
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public ResponseResult updateById(SysUser sysUserEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysUserService.updateById(sysUserEntity);
        } catch (Exception e) {
            log.error("updateById Method Exception", e);
        }
        return resultMsg;
    }


    @LogAnnotation(value = "查询用户")
    @PostMapping("/findById")
    @PreAuthorize("hasAuthority('sys:user:view')")
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
