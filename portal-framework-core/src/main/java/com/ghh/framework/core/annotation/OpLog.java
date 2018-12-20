package com.ghh.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*****************************************************************
 *
 * 方法是否需要保存日志注解
 *
 * @author ghh
 * @date 2018年12月19日下午10:21:25
 * @since v1.0.1
 ****************************************************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OpLog {
	/**
	 * 日志类型,PICTURE图片日志,RECORD数据日志,DEFAULT纯事物
	 * 
	 * @return
	 */
	public abstract GhhLogType value() default GhhLogType.DEFAULT;

	/**
	 * 日志摘要信息
	 * 
	 * @return
	 */
	public abstract String digest() default "";

	/**
	 * 模块名
	 * 
	 * @return
	 */
	public abstract String moduleName() default "";

}
