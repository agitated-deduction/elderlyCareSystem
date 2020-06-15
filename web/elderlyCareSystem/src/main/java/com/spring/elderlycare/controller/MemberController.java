package com.spring.elderlycare.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.spring.elderlycare.dto.MemberDTO;
import com.spring.elderlycare.service.MemberService;

@SessionAttributes("uid")
@RestController
@RequestMapping("/users")
public class MemberController {
	
	@Autowired private MemberService service;
	@Autowired private MemberDTO mdto;
	//private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@RequestMapping(value ="login", method = RequestMethod.GET)
	public ModelAndView memberLogin(ModelAndView mav) {
		mav.setViewName("member/login");
		return mav;
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginCheck(ModelAndView mav,
			@RequestParam("inp_m_id")String m_id,
			@RequestParam("inp_m_pwd")String m_pwd,
			HttpSession session) {
		mdto.setUid(m_id);
		mdto.setUpwd(m_pwd);
		boolean isExist = service.loginCheck(mdto);
		mav.setViewName("member/login");
		//String path  = "member/login";
		if(isExist) {
			mav.setViewName("redirect:/");
			//path = "redirect:/";
			mav.addObject("uid", mdto.getUid());
			//session.setAttribute("m_id", mdto.getM_id());
		}
		return mav;
	}
	
	//화면에서 입력 폼 json으로 받기
	@RequestMapping(value = "/login", method = RequestMethod.POST, 
			headers= {"Content-type=application/json"})
	@ResponseBody
	public ModelAndView loginCheckTemp(ModelAndView mav, @RequestBody MemberDTO mdto) {
		boolean isExist = service.loginCheck(mdto);
		mav.setViewName("member/login");
		//String path  = "member/login";
		if(isExist) {
			mav.setViewName("redirect:/");
			//path = "redirect:/";
			mav.addObject("uid", mdto.getUid());
			//session.setAttribute("m_id", mdto.getM_id());
		}
		return mav;
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView memberJoinForm(ModelAndView mav){
		mav.setViewName("member/join");
		return mav;
	}
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String memberJoin(Model model,
			@RequestParam("inp_m_id")String m_id,
			@RequestParam("inp_m_pwd")String m_pwd,
			@RequestParam("inp_m_name")String m_name,
			@RequestParam("inp_m_tel")String m_tel,
			@RequestParam("inp_m_email")String m_email) {
		String path = "redirect:/";
		
		mdto.setUid(m_id);
		mdto.setUpwd(m_pwd);
		mdto.setUname(m_name);
		mdto.setUtel(m_tel);
		mdto.setUemail(m_email);
		
		int ret = service.join(mdto);
		
		if(ret>0) 
			model.addAttribute("msg", "가입 신청 완료");
		else model.addAttribute("msg", "가입 신청 실패");
		
		return path;
	}
	
	@RequestMapping("/{uid}")
	public MemberDTO myPage(Model model, @PathVariable("uid") String m_id) {
		mdto = service.myPage(m_id);
		System.out.println(m_id);
		//model.addAttribute("mdto", mdto);
		return mdto;
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView memberLogout(SessionStatus sessionStatus, ModelAndView mav) {
		sessionStatus.setComplete();
		mav.setViewName("redirect:/");
		return mav;
	}
	@RequestMapping(value = "/devices", method = RequestMethod.GET)
	public ModelAndView deviceList(ModelAndView mav) {
		
		return mav;
	}
}
