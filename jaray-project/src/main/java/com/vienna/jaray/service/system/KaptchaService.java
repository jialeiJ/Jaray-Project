package com.vienna.jaray.service.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 验证码接口类
 */
public interface KaptchaService {
    /**
     * 验证码接口
     * @param request request对象
     * @param response response对象
     * @param session session 对象
     * @throws IOException IOException
     */
    void kaptchaImage(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException;
}
