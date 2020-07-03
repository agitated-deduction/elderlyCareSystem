package com.spring.elderlycare.service;


import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.MemberDTO;

@Component
public interface MemberService {
	
	public int join(MemberDTO mdto);
	public int loginCheck(MemberDTO mdto);
	public int modify(MemberDTO mdto);
	public int delet(String id);
	public MemberDTO myPage(String id);
}
