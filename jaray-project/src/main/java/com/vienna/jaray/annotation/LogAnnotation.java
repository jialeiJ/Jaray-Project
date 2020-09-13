package com.vienna.jaray.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jaray
 * @date 2020年09月10日 22:29
 * @description: 用户日志记录自定义注解类
 */
/* 注解放置的目标位置，METHOD是可注解在方法级别上 */
@Target(ElementType.METHOD)
/* 注解在那个阶段执行 */
@Retention(RetentionPolicy.RUNTIME)
/* 生成文档 */
@Documented
public @interface LogAnnotation {
	
	String value() default "";

}
