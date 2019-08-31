package com.tdschool.interceptor;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TokenInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelView)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO 判断是否有自定义的注解
		if(handler==null||!(handler instanceof HandlerMethod)) return true;// 代表继续执行下一步=》 进入cntroller
		UserToken token= ((HandlerMethod)handler).getMethodAnnotation(UserToken.class);
		if(token==null)return true;
		if(token.create()){
			// TODO 需要生成token
			String nostr = randomString(32);
			request.getSession().setAttribute("tokenvalue", nostr);
		}else if(token.check()){
			// TODO 需要进行token验证
			String tokenvalue = (String) request.getSession().getAttribute("tokenvalue");
			String requestToke = request.getParameter("tokenvalue");
			if(tokenvalue!=null&&tokenvalue.equals(requestToke)){
				// TODO 验证成功 直接进入下一步
			}else{
				// TODO 验证失败 返回错误信息
				return false;
			}
		}
		return true;
	}
	/**
	 * 生成指定长度的随机字符串
	 * @param length
	 * @return
	 */
	private String randomString(int length){
		if(length<1)return "";
		String str="1234567890qwertyuiopasdfghjklzxcvbnm";
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		int limitSize = str.length();
		for(int i=0;i<length;i++){
			buffer.append(str.charAt(random.nextInt(limitSize)));
		}
		return buffer.toString();
	}


}
