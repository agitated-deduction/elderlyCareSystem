package com.spring.elderlycare.dao;

import org.springframework.stereotype.Repository;

import com.spring.elderlycare.dto.MemberDTO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{
	@Override
	public void insertMember(MemberDTO mdto) {
	}
	@Override
	public void updateMember(MemberDTO mdto) {
	}
	@Override
	public void deleteMember(String id) {
	}
	@Override
	public MemberDTO selectOne(String id) {
		MemberDTO mdto = null;
		
		return mdto;
	}
	@Override
	public boolean exist(MemberDTO mdto) {
		boolean isExist = false;
		//test 
		if(mdto.getM_id().equals("test"))
			isExist = true;
		return isExist;
	}
}
