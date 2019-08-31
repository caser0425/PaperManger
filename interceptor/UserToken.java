package com.tdschool.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明当前注解使用的范围和环境
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserToken {
	/**
	 * 是否在页面生成token
	 * @return
	 */
	boolean create() default false;
	/**
	 * 是否需要去验证token的值
	 * @return
	 */
	boolean check() default false;
}
