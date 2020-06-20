package com.vienna.jaray.service;

import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.entity.SysUserTokenEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface LoginService {
    /**
     * 登录接口
     * @param request request对象
     * @param username 用户名
     * @param password 密码
     * @param captcha 验证码
     * @param session session对象
     * @return
     */
    public ResponseResult login(HttpServletRequest request, String username, String password, String captcha, HttpSession session);

    /**
     * 重新登录接口，获取token
     * @param request request对象
     * @param sysUserToken sysUserToken对象
     * @return
     */
    public ResponseResult reLogin(HttpServletRequest request, SysUserTokenEntity sysUserToken);
}
