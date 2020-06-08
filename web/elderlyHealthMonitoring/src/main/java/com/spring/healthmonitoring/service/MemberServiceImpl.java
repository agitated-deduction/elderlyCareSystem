package com.spring.healthmonitoring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.healthmonitoring.dto.MemberDto;
import com.spring.healthmonitoring.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired MemberMapper mapper;
	@Override
	public void addMember(MemberDto member) {
		mapper.insertMember(member);
	}
	@Override
	public void modifyMember(MemberDto member) {
		
	}
	@Override
	public void deleteMember(MemberDto member) {
		
	}
	@Override
	public MemberDto findById(MemberDto member) {
		
		return member;
	}
	@Override
	public boolean exist(MemberDto member) {
		boolean isExist = false;
		
		return isExist;
	}
}
