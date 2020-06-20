package com.vienna.jaray.service.impl;

import com.google.code.kaptcha.Constants;
import com.vienna.jaray.common.HttpStatus;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysUserEntity;
import com.vienna.jaray.entity.SysUserTokenEntity;
import com.vienna.jaray.mapper.SysUserMapper;
import com.vienna.jaray.security.JwtAuthenticatioToken;
import com.vienna.jaray.service.LoginService;
import com.vienna.jaray.service.SysMenuService;
import com.vienna.jaray.service.SysUserService;
import com.vienna.jaray.utils.DateTimeUtil;
import com.vienna.jaray.utils.JwtTokenUtil;
import com.vienna.jaray.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public ResponseResult login(HttpServletRequest request, String username, String password, String captcha, HttpSession session) {
        String sessionCaptcha = session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();

        log.info("{},{},{},{}",username, password, captcha, sessionCaptcha);

        if(!sessionCaptcha.equalsIgnoreCase(captcha)){
            return ResponseResult.fail().setCode(HttpStatus.Captcha_Error.getStatusCode()).setMsg(HttpStatus.Captcha_Error.getStatusDesc());
        }
        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtil.login(request, username, password, authenticationManager);

        SysUserEntity sysUserEntity = sysUserMapper.findByName(username);
        SysUserTokenEntity sysUserTokenEntity = new SysUserTokenEntity();
        sysUserTokenEntity.setUser_id(sysUserEntity.getId());
        sysUserTokenEntity.setToken(token.getToken());
        sysUserTokenEntity.setExpire_time(token.getExpireTime());
        sysUserTokenEntity.setCreate_by(sysUserEntity.getCreate_by());
        sysUserTokenEntity.setCreate_time(DateTimeUtil.parseDateToString(sysUserEntity.getCreate_time(), DateTimeUtil.DATE_FORMAT_FULL));

        return ResponseResult.success().add("sysUserToken",sysUserTokenEntity);
    }

    @Override
    public ResponseResult reLogin(HttpServletRequest request, SysUserTokenEntity sysUserToken) {
        Authentication authentication = SecurityUtil.getAuthentication();

        // 生成令牌并返回给客户端
        SysUserTokenEntity sysUserTokenEntity = new SysUserTokenEntity();
        String token = JwtTokenUtil.refreshToken(sysUserToken.getToken());
        sysUserTokenEntity.setToken(token);
        sysUserTokenEntity.setExpire_time(JwtTokenUtil.tokenExpireTime(token));

        return ResponseResult.success().add("sysUserToken",sysUserTokenEntity);
    }
}
