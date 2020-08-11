package com.vienna.jaray.interceptor;

import com.alibaba.fastjson.JSON;
import com.vienna.jaray.common.HttpStatus;
import com.vienna.jaray.common.ResponseResult;
import com.vienna.jaray.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Token拦截器
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // OPTIONS请求类型直接返回不处理
        if ("OPTIONS".equals(request.getMethod())){
            return false;
        }

        String token = JwtTokenUtil.getToken(request);
        boolean isTokenExpired = JwtTokenUtil.isTokenExpired(token);
        if(isTokenExpired){
            resultMsg(response);
            return false;
        }
        return !isTokenExpired;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(request.getContextPath() + "==========postHandle==========");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info(request.getContextPath() + "==========afterCompletion==========");
    }

    private void resultMsg(HttpServletResponse response) {
        PrintWriter pw = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try {
            pw = response.getWriter();

            pw.print(JSON.toJSONString(ResponseResult.success().setMsg(HttpStatus.SC_FORBIDDEN.getStatusDesc()).setCode(HttpStatus.SC_FORBIDDEN.getStatusCode())));
        } catch (IOException e) {
            log.error("Token拦截器输出流异常：{}",e);
        }finally {
            if(pw != null){
                pw.close();
            }
        }
    }
}
