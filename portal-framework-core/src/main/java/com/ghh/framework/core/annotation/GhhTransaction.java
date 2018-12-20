package com.ghh.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*****************************************************************
 *
 * 自定义启用事物注解
 *
 * @author ghh
 * @date 2018年12月19日下午10:20:15
 * @since v1.0.1
 ****************************************************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GhhTransaction {
	public boolean value() default true;
}
