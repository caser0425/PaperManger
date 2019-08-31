package com.tdschool.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ������ǰע��ʹ�õķ�Χ�ͻ���
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserToken {
	/**
	 * �Ƿ���ҳ������token
	 * @return
	 */
	boolean create() default false;
	/**
	 * �Ƿ���Ҫȥ��֤token��ֵ
	 * @return
	 */
	boolean check() default false;
}
