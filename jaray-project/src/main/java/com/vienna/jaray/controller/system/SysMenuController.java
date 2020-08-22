package com.vienna.jaray.controller.system;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysMenu;
import com.vienna.jaray.model.system.CommonParamsModel;
import com.vienna.jaray.service.system.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @ILogAnnotation(value = "查询菜单列表")
    @PostMapping("/find/all")
    @PreAuthorize("hasAuthority('sys:menu:view')")
    public ResponseResult findTreeAll(CommonParamsModel commonParamsModel) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysMenuService.findTreeAll(commonParamsModel);
        } catch (Exception e) {
            log.error("findTreeAll Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "添加菜单")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public ResponseResult add(SysMenu sysMenuEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysMenuService.add(sysMenuEntity);
        } catch (Exception e) {
            log.error("add Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "删除菜单")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public ResponseResult deleteByIds(String[] ids) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysMenuService.deleteByIds(ids);
        } catch (Exception e) {
            log.error("deleteByIds Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "更新菜单")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:edit')")
    public ResponseResult updateById(SysMenu sysMenuEntity) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysMenuService.updateById(sysMenuEntity);
        } catch (Exception e) {
            log.error("updateById Method Exception", e);
        }
        return resultMsg;
    }

    @ILogAnnotation(value = "查询菜单")
    @PostMapping("/find")
    @PreAuthorize("hasAuthority('sys:menu:view')")
    public ResponseResult findById(String id) {
        ResponseResult resultMsg = null;
        try {
            resultMsg = sysMenuService.findById(id);
        } catch (Exception e) {
            log.error("findById Method Exception", e);
        }
        return resultMsg;
    }
}
