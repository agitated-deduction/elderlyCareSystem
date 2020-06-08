package com.spring.healthmonitoring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.healthmonitoring.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/memberLogin")
	public String memberLogin(HttpServletRequest request, HttpServletResponse response)throws Exception{
		memberService.login();
		return "member/memeberLogin";
	}
	@RequestMapping(value = "/memberJoin")
	public String memberJoin(HttpServletRequest request, HttpServletResponse response)throws Exception{
		memberService.join();
		return "member/memeberJoin";
	}
	
	
}
