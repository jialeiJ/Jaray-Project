package com.vienna.jaray.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.entity.SysUserTokenEntity;
import com.vienna.jaray.security.JwtAuthenticatioToken;
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
    private Producer captchaProducer = null;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/captcha.jpg")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @PostMapping("/login")
    public ResponseResult login(HttpServletRequest request, String username, String password, String captcha) {
        log.info("{},{},{}",username, password, captcha);
        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtil.login(request, username, password, authenticationManager);

        SysUserEntity sysUserEntity = sysUserService.findSysUser(username);
        SysUserTokenEntity sysUserTokenEntity = new SysUserTokenEntity();
        sysUserTokenEntity.setUser_id(sysUserEntity.getId());
        sysUserTokenEntity.setToken(token.getToken());
        sysUserTokenEntity.setCreate_by(username);
        sysUserTokenEntity.setCreate_time(new Date());

        return ResponseResult.success().add("sysUserEntity",sysUserEntity).add("token",token.getToken());
    }

    @PostMapping("/findLeftNav")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseResult findLeftNav(){
        return sysMenuService.findLeftNav();
    }

    @PostMapping("/refreshToken")
    public ResponseResult validateAndrefreshToken(String token){
        String refreshToken = JwtTokenUtil.validateAndrefreshToken(token);
        log.info(refreshToken);
        return ResponseResult.success().add("refreshToken", refreshToken);
    }

}
