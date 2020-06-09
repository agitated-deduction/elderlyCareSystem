package com.spring.elderlycare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.elderlycare.dao.MemberDAO;
import com.spring.elderlycare.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDAO mdao;
	
	@Override
	public int join(MemberDTO mdto) {
		int ret = 0;
		
		mdao.insertMember(mdto);
		
		return ret;
	}
	@Override
	public boolean loginCheck(MemberDTO mdto){
		boolean isExist = mdao.exist(mdto);
		return isExist;
	}
	@Override
	public int modify(MemberDTO mdto){
		int ret = 0;
		mdao.updateMember(mdto);
		return ret;
	}
	@Override
	public int delet(String id){
		int ret = 0;
		mdao.deleteMember(id);
		return ret;
	}
	@Override
	public MemberDTO myPage(String id){
		MemberDTO mdto = null;
		mdto = mdao.selectOne(id);
		return mdto;
	}
}