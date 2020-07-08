package com.spring.elderlycare.dao;


import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.MemberDTO;

@Component
public interface MemberDAO {
	public int insertMember(MemberDTO mdto);
	public int updateMember(MemberDTO mdto);
	public int deleteMember(String id);
	public MemberDTO selectOne(String id);
	public int exist(MemberDTO mdto);
}
