package com.spring.healthmonitoring.service;

import org.springframework.stereotype.Component;

import com.spring.healthmonitoring.dto.MemberDto;

@Component
public interface MemberService{
	public void addMember(MemberDto member);
	public void modifyMember(MemberDto member);
	public void deleteMember(MemberDto member);
	public MemberDto findById(MemberDto member);
	public boolean exist(MemberDto member);
}
