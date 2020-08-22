package com.vienna.jaray.security;

import com.alibaba.fastjson.JSON;
import com.vienna.jaray.common.HttpStatus;
import com.vienna.jaray.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class JwtAuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ae) throws IOException, ServletException {
        log.info("权限不足");
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
