package com.vienna.jaray.controller;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysRoleMenu;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.SysRoleMenuPermService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/role/menu/perm")
public class SysRoleMenuPermController {
    @Autowired
    private SysRoleMenuPermService sysRoleMenuPermService;

    @ILogAnnotation(value = "查询角色菜单权限列表")
    @PostMapping("/find/all")
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleMenuPermService.findAll(commonParamsModel);
        } catch (Exception e) {
            log.error("findAll Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "添加角色菜单权限")
    @PostMapping("/add")
    public ResponseResult add(SysRoleMenu sysRoleMenuEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleMenuPermService.add(sysRoleMenuEntity);
        } catch (Exception e) {
            log.error("add Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "删除角色菜单权限")
    @PostMapping("/delete")
    public ResponseResult deleteByRids(String role_ids) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleMenuPermService.deleteByIds(role_ids.split(","));
        } catch (Exception e) {
            log.error("deleteByRids Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "更新角色菜单权限")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public ResponseResult updateByRid(String role_id, String menu_perm_id) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleMenuPermService.updateByRid(role_id, menu_perm_id);
        } catch (Exception e) {
            log.error("updateByRid Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "查询角色菜单权限")
    @PostMapping("/findByRid")
    public ResponseResult findByRid(String role_id) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleMenuPermService.findByRid(role_id);
        } catch (Exception e) {
            log.error("findByRid Method Exception", e);
        }
        return resultMsg;
    }
}
