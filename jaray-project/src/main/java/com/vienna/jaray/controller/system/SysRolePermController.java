package com.vienna.jaray.controller.system;

import com.vienna.jaray.annotation.LogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysRolePerm;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.system.SysRolePermService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统角色权限控制器
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/role/perm")
public class SysRolePermController {
    @Autowired
    private SysRolePermService sysRolePermService;

    @LogAnnotation(value = "查询角色权限列表")
    @PostMapping("/find/all")
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRolePermService.findAll(commonParamsModel);
        } catch (Exception e) {
            log.error("findAll Method Exception", e);
        }
        return resultMsg;
    }

    @LogAnnotation(value = "添加角色权限")
    @PostMapping("/add")
    public ResponseResult add(SysRolePerm sysRolePermEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRolePermService.add(sysRolePermEntity);
        } catch (Exception e) {
            log.error("add Method Exception", e);
        }
        return resultMsg;
    }

    @LogAnnotation(value = "删除角色权限")
    @PostMapping("/delete")
    public ResponseResult deleteByIds(String ids) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRolePermService.deleteByIds(ids.split(","));
        } catch (Exception e) {
            log.error("deleteByIds Method Exception", e);
        }
        return resultMsg;
    }

    @LogAnnotation(value = "更新角色权限")
    @PostMapping("/update")
    public ResponseResult updateByRid(SysRolePerm sysRolePermEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRolePermService.updateByRid(sysRolePermEntity);
        } catch (Exception e) {
            log.error("updateByRid Method Exception", e);
        }
        return resultMsg;
    }

    @LogAnnotation(value = "查询角色权限")
    @PostMapping("/findByRid")
    public ResponseResult findByRid(String roleId) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysRolePermService.findByRid(roleId);
        } catch (Exception e) {
            log.error("findByRid Method Exception", e);
        }
        return resultMsg;
    }
}
