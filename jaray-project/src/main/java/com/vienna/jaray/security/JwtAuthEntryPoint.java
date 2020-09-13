package com.vienna.jaray.security;

import com.alibaba.fastjson.JSON;
import com.vienna.jaray.common.HttpStatus;
import com.vienna.jaray.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 认证失败处理类
 */
@Slf4j
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ae) throws IOException, ServletException {
        log.info("认证失败");
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
