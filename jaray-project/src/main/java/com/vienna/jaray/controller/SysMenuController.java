package com.vienna.jaray.controller;

import com.vienna.jaray.annotation.ILogAnnotation;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysMenuEntity;
import com.vienna.jaray.entity.SysUserTokenEntity;
import com.vienna.jaray.model.CommonParamsModel;
import com.vienna.jaray.service.KaptchaService;
import com.vienna.jaray.service.LoginService;
import com.vienna.jaray.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    public ResponseResult add(SysMenuEntity sysMenuEntity) {
        return sysMenuService.add(sysMenuEntity);
    }

    @ILogAnnotation(value = "删除菜单")
    @PostMapping("/delete")
    public ResponseResult deleteByIds(String[] ids) {
        return sysMenuService.deleteByIds(ids);
    }

    @ILogAnnotation(value = "更新菜单")
    @PostMapping("/update")
    public ResponseResult updateById(SysMenuEntity sysMenuEntity) {
        return sysMenuService.updateById(sysMenuEntity);
    }

    @ILogAnnotation(value = "查询菜单")
    @PostMapping("/find")
    public ResponseResult findById(String id) {
        return sysMenuService.findById(id);
    }

}
