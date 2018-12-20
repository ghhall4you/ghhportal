package com.ghh.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*****************************************************************
 *
 * 表单重复提交token
 *
 * @author ghh
 * @date 2018年12月19日下午10:19:58
 * @since v1.0.1
 ****************************************************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GhhToken {
	public boolean value() default true;
}
