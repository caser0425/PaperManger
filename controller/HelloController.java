package com.tdschool.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tdschool.interceptor.UserToken;

/**
 * ������������ http://localhost:8088/PaperManager/index/*  ȫ��ת����HelloController��
 * ���� HOST���� http://localhost:8088/PaperManager/index/
 * ������GET����
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value={"index"},method={RequestMethod.GET})
public class HelloController {
	/**
	 * ����Ƿ��¼
	 * @param session
	 * @return
	 */
	private boolean isLogin(HttpSession session){
		return "login".equals(session.getAttribute("loginState"));
	}
	/**
	 * ��¼����
	 * @return
	 */
	@UserToken(create=true,check=false)
	@RequestMapping(value={"login"})  // ����HOST/login �������������õ�ǰ�ķ���
	public String login(HttpSession session,ModelMap model,@ModelAttribute("errorMsg") String errorMsg){
		if(isLogin(session))return "redirect:/index/index";
		String valicode = randomString(6);
		session.setAttribute("valicode", valicode);
		model.addAttribute("valicode", valicode);
		model.addAttribute("errorMsg", errorMsg);
		return "login";
	}
	/**
	 * ��ҳ
	 * @return
	 */
	@RequestMapping({"index",""})  // ����HOST/index �������������õ�ǰ�ķ���
	public String index(HttpSession session){
		if(!isLogin(session))return "redirect:/index/login";
		return "index";
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
