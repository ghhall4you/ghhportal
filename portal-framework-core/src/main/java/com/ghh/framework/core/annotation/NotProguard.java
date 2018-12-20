package com.ghh.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*****************************************************************
 *
 * 无需代码混淆注解
 *
 * @author ghh
 * @date 2018年12月19日下午10:20:42
 * @since v1.0.1
 ****************************************************************/
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD })
public @interface NotProguard {

}
