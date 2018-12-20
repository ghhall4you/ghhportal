package com.ghh.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*****************************************************************
 *
 * EXCEL导入导出切入点
 *
 * @author ghh
 * @date 2018年12月19日下午10:19:16
 * @since v1.0.1
 ****************************************************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelIO {
	public abstract String value();
}
