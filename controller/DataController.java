package com.tdschool.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tdschool.interceptor.UserToken;

/**
 * 请求连接满足 http://localhost:8080/PaperManager/index/*  全部转发到HelloController中
 * 以下 HOST代表 http://localhost:8080/PaperManager/index/
 * 必须是POST请求
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="index",method={RequestMethod.POST})
public class DataController {
	/**
	 * 登录管理
	 * @param username
	 * @param userpass
	 * @param valicode
	 * @return
	 */
	@UserToken(check=true,create=false)
	@RequestMapping("loginDeal")
	public String loginDeal(String username,String userpass,String valicode,HttpSession session,RedirectAttributes attributes){
		//TODO 验证验证码
		String val = (String) session.getAttribute("valicode");
		// 验证码验证失败
		if(val==null||val.isEmpty()||!val.equals(valicode)){
			// 添加错误提示信息
			attributes.addFlashAttribute("errorMsg", "验证码错误");
			return "redirect:/index/login";
		}
		//TODO 验证登录
		if("username".equals(username)&&"123456".equals(userpass)){
			// 记录登录状态
			session.setAttribute("loginState", "login");
			// 登录成功返回index 重定向返回
			return "redirect:/index/index";
		}
		// 添加错误提示信息
		attributes.addFlashAttribute("errorMsg", "用户名不存在或密码错误");
		// 登录失败返回login 重定向返回
		return "redirect:/index/login";
	}
	/**
	 * 刷新验证码
	 * @param session
	 * @return
	 */
	@RequestMapping("refreshValiCode")
	@ResponseBody
	public String refreshValiCode(HttpSession session){
		// 生成验证码
		String nostr = randomString(6);
		// 验证码写入session中
		session.setAttribute("valicode", nostr);
		// 验证码返回给ajax
		return nostr;
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

