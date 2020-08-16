package com.vienna.jaray.aspect;

import java.lang.reflect.Method;

import com.vienna.jaray.entity.system.SysLog;
import com.vienna.jaray.service.SysLogService;
import com.vienna.jaray.utils.DateTimeUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.vienna.jaray.annotation.ILogAnnotation;

/**
 * @Title: ILogAspect.java
 * @Description: 用户日志切面处理类
 * @author Administrator
 * @date 2019年10月13日下午10:34:46
 */
@Aspect
@Component
public class ILogAspect {
	@Autowired
	private SysLogService sysLogService;
	
	/* 定义切点@Pointcut */
	/* 在注解的位置切入代码 */
	@Pointcut("@annotation( com.vienna.jaray.annotation.ILogAnnotation)")
	public void logPoinCut() {
		
	}
	
	//切面 配置通知
    @AfterReturning("logPoinCut()")
	public void saveILog(JoinPoint joinPoint) {
		
		//保存日志
		SysLog sysLog = new SysLog();
		
		//从切面织入点处通过反射机制获取织入点处的方法
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		//获取切入点所在的方法
		Method method = methodSignature.getMethod();
		
		//获取操作
		ILogAnnotation iLogAnnotation = method.getAnnotation(ILogAnnotation.class);
		if(iLogAnnotation != null) {
			String value = iLogAnnotation.value();
			/* 保存获取的操作 */
			sysLog.setOperation(value);
		}
		
		//获取请求的类名
		String className = joinPoint.getTarget().getClass().getName();
		//获取请求的方法名
		String methodName = method.getName();
		sysLog.setMethod(className +"."+ methodName);
		
		//请求的参数
		Object[] args = joinPoint.getArgs();
		//将参数所在的数组转成json
		String params = new Gson().toJson(args);
		sysLog.setParams(params);

		sysLog.setCreate_time(DateTimeUtil.getCurDateTime(DateTimeUtil.DATE_FORMAT_FULL));
		//获取用户名
		sysLog.setUser_name("Jaray");
		
		//获取用户ip地址
		sysLog.setIp("");
		
		//保存ILog实体类到数据库
		sysLogService.add(sysLog);
		
		System.out.println(sysLog);
	}

}
