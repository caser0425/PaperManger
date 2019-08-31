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
		// TODO �ж��Ƿ����Զ����ע��
		if(handler==null||!(handler instanceof HandlerMethod)) return true;// �������ִ����һ��=�� ����cntroller
		UserToken token= ((HandlerMethod)handler).getMethodAnnotation(UserToken.class);
		if(token==null)return true;
		if(token.create()){
			// TODO ��Ҫ����token
			String nostr = randomString(32);
			request.getSession().setAttribute("tokenvalue", nostr);
		}else if(token.check()){
			// TODO ��Ҫ����token��֤
			String tokenvalue = (String) request.getSession().getAttribute("tokenvalue");
			String requestToke = request.getParameter("tokenvalue");
			if(tokenvalue!=null&&tokenvalue.equals(requestToke)){
				// TODO ��֤�ɹ� ֱ�ӽ�����һ��
			}else{
				// TODO ��֤ʧ�� ���ش�����Ϣ
				return false;
			}
		}
		return true;
	}
	/**
	 * ����ָ�����ȵ�����ַ���
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
