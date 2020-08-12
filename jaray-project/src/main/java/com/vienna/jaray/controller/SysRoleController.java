package com.vienna.jaray.controller;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysRole;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ILogAnnotation(value = "查询角色列表")
    @PostMapping("/find/all")
    @PreAuthorize("hasAuthority('sys:role:view')")
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleService.findAll(commonParamsModel);
        } catch (Exception e) {
            log.error("findAll Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "添加角色")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:role:add')")
    public ResponseResult add(SysRole sysRoleEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleService.add(sysRoleEntity);
        } catch (Exception e) {
            log.error("add Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "删除角色")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public ResponseResult deleteByIds(String ids) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleService.deleteByIds(ids.split(","));
        } catch (Exception e) {
            log.error("deleteByIds Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "更新角色")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public ResponseResult updateById(SysRole sysRoleEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleService.updateById(sysRoleEntity);
        } catch (Exception e) {
            log.error("updateById Method Exception", e);
        }
        return resultMsg;
    }


    @ILogAnnotation(value = "查询角色")
    @PostMapping("/find")
    @PreAuthorize("hasAuthority('sys:role:view')")
    public ResponseResult findById(String id) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleService.findById(id);
        } catch (Exception e) {
            log.error("findById Method Exception", e);
        }
        return resultMsg;
    }
}
