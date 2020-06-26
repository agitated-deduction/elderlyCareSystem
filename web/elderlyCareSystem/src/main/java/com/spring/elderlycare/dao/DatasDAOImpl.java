package com.spring.elderlycare.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.elderlycare.dto.DatasDTO;

@Repository("datasDAO")
public class DatasDAOImpl implements DatasDAO{
	@Autowired
	private SqlSession sqlSession;
	@Autowired DatasDTO datasdto;
	
	private static final String ns = "com.spring.elderlycare.dao.DatasDAOImpl.";
	@Override
	public void insertDataEvent(DatasDTO datasdto) {
		sqlSession.insert(ns+"log", datasdto);
	}
}