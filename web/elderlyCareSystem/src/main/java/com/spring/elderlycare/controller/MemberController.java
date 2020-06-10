package com.spring.elderlycare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.spring.elderlycare.dto.MemberDTO;
import com.spring.elderlycare.service.MemberService;

@Controller
@SessionAttributes("m_id")
public class MemberController {
	
	@Autowired private MemberService service;
	@Autowired private MemberDTO mdto;
	//private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping(value ="/memberLogin", method = RequestMethod.GET)
	public String memberLogin(Model model) {
		return "member/login";
	}
	@RequestMapping(value = "/memberLogin", method = RequestMethod.POST)
	public String loginCheck(Model model,
			@RequestParam("inp_m_id")String m_id,
			@RequestParam("inp_m_pwd")String m_pwd) {
		mdto.setM_id(m_id);
		mdto.setM_pwd(m_pwd);
		boolean isExist = service.loginCheck(mdto);
		String path  = "member/login";
		if(isExist) {
			path = "redirect:/";
			model.addAttribute("m_id", mdto.getM_id());
		}
		return path;
	}
	
	@RequestMapping(value = "/memberJoin", method = RequestMethod.GET)
	public String memberJoinForm(Model model){
		return "member/join";
	}
	@RequestMapping(value = "/memberJoin", method = RequestMethod.POST)
	public String memberJoin(Model model,
			@RequestParam("inp_m_id")String m_id,
			@RequestParam("inp_m_pwd")String m_pwd,
			@RequestParam("inp_m_name")String m_name,
			@RequestParam("inp_m_tel")String m_tel,
			@RequestParam("inp_m_email")String m_email) {
		String path = "redirect:/";
		
		mdto.setM_id(m_id);
		mdto.setM_pwd(m_pwd);
		mdto.setM_name(m_name);
		mdto.setM_tel(m_tel);
		mdto.setM_email(m_email);
		
		int ret = service.join(mdto);
		
		if(ret>0) 
			model.addAttribute("msg", "가입 신청 완료");
		else model.addAttribute("msg", "가입 신청 실패");
		
		return path;
	}
	
	@RequestMapping("/mypage")
	public String myPage(Model model, @SessionAttribute("m_id") String m_id) {
		mdto = service.myPage(m_id);
		model.addAttribute("mdto", mdto);
		return "member/myPage";
	}
}
