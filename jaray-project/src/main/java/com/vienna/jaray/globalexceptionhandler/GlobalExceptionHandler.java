package com.vienna.jaray.globalexceptionhandler;

import com.vienna.jaray.common.ResponseResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebResult;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice // 控制器增强类，当控制器发生异常且符合类中定义的拦截异常类，将会被拦截。可以定义拦截的控制器所在的包路径
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value =Exception.class)
    public ResponseResultMsg globalException(HttpServletResponse response, Exception e){
        log.info("GlobalExceptionHandler...{}", e.getMessage());
        String msg = "GlobalExceptionHandler:"+e.getMessage();
        return ResponseResultMsg.fail().setMsg(msg);
    }
}