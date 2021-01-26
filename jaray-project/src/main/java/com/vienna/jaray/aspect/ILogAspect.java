package com.vienna.jaray.aspect;

import java.lang.reflect.Method;

import com.vienna.jaray.entity.system.SysLog;
import com.vienna.jaray.service.system.SysLogService;
import com.vienna.jaray.utils.DateTimeUtil;
import com.vienna.jaray.utils.IpUtils;
import com.vienna.jaray.utils.JwtTokenUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.vienna.jaray.annotation.LogAnnotation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 用户日志切面处理类
 */
@Aspect
@Component
public class LogAspect {
	@Autowired
	private SysLogService sysLogService;

	/**
	 * 定义切点@Pointcut
	 * 在注解的位置切入代码
	 */
	@Pointcut("@annotation( com.vienna.jaray.annotation.LogAnnotation)")
	public void logPointCut() {
		
	}

	/**
	 * 切面 配置通知
	 * @param joinPoint 切点
	 */
    @AfterReturning("logPointCut()")
	public void saveLog(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String token = JwtTokenUtil.getToken(request);
		String userName = JwtTokenUtil.getUsernameFromToken(token);
		
		// 保存日志操作
		SysLog sysLog = new SysLog();
		
		// 从切面织入点处通过反射机制获取织入点处的方法
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		// 获取切入点所在的方法
		Method method = methodSignature.getMethod();
		
		// 获取操作
		LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
		if(logAnnotation != null) {
			String value = logAnnotation.value();
			/* 保存获取的操作 */
			sysLog.setOperation(value);
		}
		
		// 获取请求的类名
		String className = joinPoint.getTarget().getClass().getName();
		// 获取请求的方法名
		String methodName = method.getName();
		sysLog.setMethod(className +"."+ methodName);
		
		// 请求的参数
		Object[] args = joinPoint.getArgs();
		// 将参数所在的数组转成json
		String params = new Gson().toJson(args);
		sysLog.setParams(params);
		sysLog.setCreateTime(DateTimeUtil.getCurDateTime(DateTimeUtil.DATE_FORMAT_FULL));
		// 获取用户名
		sysLog.setUserName(userName);
		// 获取用户ip地址
		sysLog.setIp(IpUtils.getIpAddr(request));
		// 保存ILog实体类到数据库
		sysLogService.add(sysLog);
	}
}
