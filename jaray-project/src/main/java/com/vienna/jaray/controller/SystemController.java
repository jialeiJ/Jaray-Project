package com.vienna.jaray.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.entity.SysUserTokenEntity;
import com.vienna.jaray.security.JwtAuthenticatioToken;
import com.vienna.jaray.service.KaptchaService;
import com.vienna.jaray.service.LoginService;
import com.vienna.jaray.service.SysMenuService;
import com.vienna.jaray.service.SysUserService;
import com.vienna.jaray.utils.JwtTokenUtil;
import com.vienna.jaray.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Date;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private KaptchaService kaptchaService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/captcha.jpg")
    public void kaptchaImage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        kaptchaService.kaptchaImage(request, response, session);
    }

    @PostMapping("/login")
    public ResponseResult login(HttpServletRequest request, String username, String password, String captcha, HttpSession session) {
        return loginService.login(request, username, password, captcha, session);
    }

    @PostMapping("/findLeftNav")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseResult findLeftNav(){
        return sysMenuService.findLeftNav();
    }

    @PostMapping("/refreshToken")
    public ResponseResult refreshToken(HttpServletRequest request, SysUserTokenEntity sysUserToken){
        return loginService.reLogin(request, sysUserToken);
    }

}
