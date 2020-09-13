package com.vienna.jaray.controller.system;

import com.vienna.jaray.annotation.LogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRoleMenu;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.system.SysRoleMenuPermService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统角色菜单权限控制器
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/role/menu/perm")
public class SysRoleMenuPermController {
    @Autowired
    private SysRoleMenuPermService sysRoleMenuPermService;

    @LogAnnotation(value = "查询角色菜单权限列表")
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

    @LogAnnotation(value = "添加角色菜单权限")
    @PostMapping("/add")
    public ResponseResult add(SysRoleMenu sysRoleMenu) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleMenuPermService.add(sysRoleMenu);
        } catch (Exception e) {
            log.error("add Method Exception", e);
        }
        return resultMsg;
    }

    @LogAnnotation(value = "删除角色菜单权限")
    @PostMapping("/delete")
    public ResponseResult deleteByRids(String roleIds) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleMenuPermService.deleteByIds(roleIds.split(","));
        } catch (Exception e) {
            log.error("deleteByRids Method Exception", e);
        }
        return resultMsg;
    }

    @LogAnnotation(value = "更新角色菜单权限")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:edit')")
    public ResponseResult updateByRid(String roleId, String menuPermId) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleMenuPermService.updateByRid(roleId, menuPermId);
        } catch (Exception e) {
            log.error("updateByRid Method Exception", e);
        }
        return resultMsg;
    }

    @LogAnnotation(value = "查询角色菜单权限")
    @PostMapping("/findByRid")
    public ResponseResult findByRid(String roleId) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRoleMenuPermService.findByRid(roleId);
        } catch (Exception e) {
            log.error("findByRid Method Exception", e);
        }
        return resultMsg;
    }
}
