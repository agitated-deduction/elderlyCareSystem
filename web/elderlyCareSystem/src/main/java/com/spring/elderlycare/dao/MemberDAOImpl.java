package com.spring.elderlycare.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.elderlycare.dto.DeviceUserDTO;
import com.spring.elderlycare.dto.MemberDTO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{
	
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	MemberDTO mdto;
	private static final String ns = "com.spring.elderlycare.dao.MemberDAOImpl.";
	@Override
	public void insertMember(MemberDTO mdto) {
		sqlSession.insert(ns+"insertMember", mdto);
		
	}
	@Override
	public void updateMember(MemberDTO mdto) {
		sqlSession.update(ns+"modifyMember", mdto);
	}
	@Override
	public void deleteMember(MemberDTO mdto) {
		sqlSession.delete(ns+"deleteMember", mdto);
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
	@Override
	public List<DeviceUserDTO> selectManageDevices(String id) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(ns+"selectDevices", id);
	}
}
