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
 * ������������ http://localhost:8080/PaperManager/index/*  ȫ��ת����HelloController��
 * ���� HOST���� http://localhost:8080/PaperManager/index/
 * ������POST����
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="index",method={RequestMethod.POST})
public class DataController {
	/**
	 * ��¼����
	 * @param username
	 * @param userpass
	 * @param valicode
	 * @return
	 */
	@UserToken(check=true,create=false)
	@RequestMapping("loginDeal")
	public String loginDeal(String username,String userpass,String valicode,HttpSession session,RedirectAttributes attributes){
		//TODO ��֤��֤��
		String val = (String) session.getAttribute("valicode");
		// ��֤����֤ʧ��
		if(val==null||val.isEmpty()||!val.equals(valicode)){
			// ��Ӵ�����ʾ��Ϣ
			attributes.addFlashAttribute("errorMsg", "��֤�����");
			return "redirect:/index/login";
		}
		//TODO ��֤��¼
		if("username".equals(username)&&"123456".equals(userpass)){
			// ��¼��¼״̬
			session.setAttribute("loginState", "login");
			// ��¼�ɹ�����index �ض��򷵻�
			return "redirect:/index/index";
		}
		// ��Ӵ�����ʾ��Ϣ
		attributes.addFlashAttribute("errorMsg", "�û��������ڻ��������");
		// ��¼ʧ�ܷ���login �ض��򷵻�
		return "redirect:/index/login";
	}
	/**
	 * ˢ����֤��
	 * @param session
	 * @return
	 */
	@RequestMapping("refreshValiCode")
	@ResponseBody
	public String refreshValiCode(HttpSession session){
		// ������֤��
		String nostr = randomString(6);
		// ��֤��д��session��
		session.setAttribute("valicode", nostr);
		// ��֤�뷵�ظ�ajax
		return nostr;
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

