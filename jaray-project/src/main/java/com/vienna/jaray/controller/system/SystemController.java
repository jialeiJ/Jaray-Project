package com.vienna.jaray.controller.system;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.system.SysUserToken;
import com.vienna.jaray.service.system.KaptchaService;
import com.vienna.jaray.service.system.SystemService;
import com.vienna.jaray.service.system.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 系统控制器
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private KaptchaService kaptchaService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/captcha.jpg")
    public void kaptchaImage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        kaptchaService.kaptchaImage(request, response, session);
    }

    @PostMapping("/login")
    public ResponseResult login(HttpServletRequest request, String username, String password, String captcha, HttpSession session) {
        return systemService.login(request, username, password, captcha, session);
    }

    @PostMapping("/findLeftNav")
    public ResponseResult findLeftNav(String userId){
        return sysMenuService.findLeftNav(userId);
    }

    @PostMapping("/refreshToken")
    public ResponseResult refreshToken(HttpServletRequest request, SysUserToken sysUserToken){
        return systemService.reLogin(request, sysUserToken);
    }

    @PostMapping("/updatePassword")
    public ResponseResult updatePassword(String id, String password){
        return systemService.updatePassword(id, password);
    }

}
