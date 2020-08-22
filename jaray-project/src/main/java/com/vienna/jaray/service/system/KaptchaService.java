package com.vienna.jaray.service.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface KaptchaService {
    /**
     * 验证码接口
     * @param request request对象
     * @param response response对象
     * @param session session 对象
     */
    public void kaptchaImage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException;
}
