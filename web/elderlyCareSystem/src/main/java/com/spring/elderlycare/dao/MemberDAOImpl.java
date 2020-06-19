package com.spring.elderlycare.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.elderlycare.dto.MemberDTO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{
	
	@Inject
	private SqlSession sqlSession;
	@Autowired
	MemberDTO mdto;
	private static final String ns = "com.spring.elderlycare.dao.MemberDAOImpl.";
	@Override
	public int insertMember(MemberDTO mdto) {
		return sqlSession.insert(ns+"insertMember", mdto);
		
	}
	@Override
	public int updateMember(MemberDTO mdto) {
		return sqlSession.update(ns+"modifyMember", mdto);
	}
	@Override
	public int deleteMember(String id) {
		return sqlSession.delete(ns+"deleteMember", id);
	}
	@Override
	public MemberDTO selectOne(String id) {
		mdto = sqlSession.selectOne(ns+"selectOne", id);
		return mdto;
	}
	@Override
	public boolean exist(MemberDTO mdto) {
		//System.out.printf("%d", (int)sqlSession.selectOne(ns+"isExist", mdto));
//		
//		return false;
		return (boolean)sqlSession.selectOne(ns+"isExist", mdto);
	}
	
}
