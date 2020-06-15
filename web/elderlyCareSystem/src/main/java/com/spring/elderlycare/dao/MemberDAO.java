package com.spring.elderlycare.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.DeviceUserDTO;
import com.spring.elderlycare.dto.MemberDTO;

@Component
public interface MemberDAO {
	public void insertMember(MemberDTO mdto);
	public void updateMember(MemberDTO mdto);
	public void deleteMember(MemberDTO mdto);
	public MemberDTO selectOne(String id);
	public boolean exist(MemberDTO mdto);
	public List<DeviceUserDTO>selectManageDevices(String id);
}
