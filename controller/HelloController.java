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
 * 请求连接满足 http://localhost:8088/PaperManager/index/*  全部转发到HelloController中
 * 以下 HOST代表 http://localhost:8088/PaperManager/index/
 * 必须是GET请求
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value={"index"},method={RequestMethod.GET})
public class HelloController {
	/**
	 * 检测是否登录
	 * @param session
	 * @return
	 */
	private boolean isLogin(HttpSession session){
		return "login".equals(session.getAttribute("loginState"));
	}
	/**
	 * 登录界面
	 * @return
	 */
	@UserToken(create=true,check=false)
	@RequestMapping(value={"login"})  // 满足HOST/login 的连接请求会调用当前的方法
	public String login(HttpSession session,ModelMap model,@ModelAttribute("errorMsg") String errorMsg){
		if(isLogin(session))return "redirect:/index/index";
		String valicode = randomString(6);
		session.setAttribute("valicode", valicode);
		model.addAttribute("valicode", valicode);
		model.addAttribute("errorMsg", errorMsg);
		return "login";
	}
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping({"index",""})  // 满足HOST/index 的连接请求会调用当前的方法
	public String index(HttpSession session){
		if(!isLogin(session))return "redirect:/index/login";
		return "index";
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
