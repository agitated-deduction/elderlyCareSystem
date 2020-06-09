package com.spring.elderlycare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.elderlycare.dto.MemberDTO;
import com.spring.elderlycare.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired private MemberService service;
	@Autowired private MemberDTO mdto;
	//private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping(value ="/memberLogin", method = RequestMethod.GET)
	public String memberLogin(Model model) {
		return "member/login";
	}
	
	
	@RequestMapping("/memberJoin")
	public String memberJoin(Model model){
		return "member/join";
	}
	@RequestMapping(value = "/memberCheck", method = RequestMethod.POST)
	public String loginCheck(Model model,
			@RequestParam("inp_m_id")String m_id,
			@RequestParam("inp_m_pwd")String m_pwd) {
		mdto.setM_id(m_id);
		mdto.setM_pwd(m_pwd);
		boolean isExist = service.loginCheck(mdto);
		String path  = "redirect:/memberLogin";
		if(isExist) {
			path = "redirect:/";
		}
		return path;
	}
	@RequestMapping("/mypage")
	public String myPage() {
		
		return "member/mypage";
	}
}
