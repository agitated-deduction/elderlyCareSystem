package com.spring.elderlycare.dao;

import java.sql.Date;
import java.util.Map;

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
	/*@Override
	public int insertMember(Map<String, Object> map) {
		return sqlSession.insert(ns+"insertMember", map);
		
	}*/
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
	public int exist(MemberDTO mdto) {
		//System.out.printf("%d", (int)sqlSession.selectOne(ns+"isExist", mdto));
		try {
			return sqlSession.selectOne(ns+"isExist", mdto);
		}catch(NullPointerException e) {
			return -2;
		}
	}
	@Override
	public int insertRelation(Map<String, Object> map) {//throws Exception{
		int ret = sqlSession.insert(ns+"insertRel", map);
		//if(ret <1)
			//throw new Exception("rollback");
		System.out.println(ret);
		System.out.println((Date)map.get("ebirth"));
		return ret;
	}
	
}
