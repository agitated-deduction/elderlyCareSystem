package com.spring.elderlycare.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.DeviceUserDTO;
import com.spring.elderlycare.dto.MemberDTO;

@Component
public interface MemberService {
	
	public int join(MemberDTO mdto);
	public boolean loginCheck(MemberDTO mdto);
	public int modify(MemberDTO mdto);
	public int delet(MemberDTO mdto);
	public MemberDTO myPage(String id);
	public List<DeviceUserDTO>devicesList(String id);
}
