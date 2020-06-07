package com.vienna.jaray.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: ILog.java
 * @Description: 用户日志记录自定义注解类
 * @author Administrator
 * @date 2019年10月13日下午10:26:04
 */
/* 注解放置的目标位置，METHOD是可注解在方法级别上 */
@Target(ElementType.METHOD)
/* 注解在那个阶段执行 */
@Retention(RetentionPolicy.RUNTIME)
/* 生成文档 */
@Documented
public @interface ILogAnnotation {
	
	String value() default "";

}
