package com.vienna.jaray.controller;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysMenu;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseResult findAll(CommonParamsModel commonParamsModel) {
        return sysMenuService.findAll(commonParamsModel);
    }

    @ILogAnnotation(value = "添加菜单")
    @PostMapping("/add")
    public ResponseResult add(SysMenu sysMenuEntity) {
        return sysMenuService.add(sysMenuEntity);
    }

    @ILogAnnotation(value = "删除菜单")
    @PostMapping("/delete")
    public ResponseResult deleteByIds(String[] ids) {
        return sysMenuService.deleteByIds(ids);
    }

    @ILogAnnotation(value = "更新菜单")
    @PostMapping("/update")
    public ResponseResult updateById(SysMenu sysMenuEntity) {
        return sysMenuService.updateById(sysMenuEntity);
    }

    @ILogAnnotation(value = "查询菜单")
    @PostMapping("/find")
    public ResponseResult findById(String id) {
        return sysMenuService.findById(id);
    }

    @ILogAnnotation(value = "查询所有目录菜单")
    @PostMapping("/dir/find/all")
    public ResponseResult findAllDir(){
        return sysMenuService.findAllDir();
    }

}
