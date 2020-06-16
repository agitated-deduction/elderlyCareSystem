package com.spring.elderlycare.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.elderlycare.dto.ElderlyDTO;

@Repository("deviceDAO")
public class DeviceDAOImpl implements DeviceDAO{
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	ElderlyDTO dudto;
	private static final String ns = "com.spring.elderlycare.dao.DeviceDAOImpl.";
	
	@Override
	public List<ElderlyDTO> selectList(String id) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(ns+"selectDevices", id);
	}

	@Override
	public ElderlyDTO selectOne(int dnum) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(ns+"selectDevice", dnum);
	}

	@Override
	public void insertDevice(ElderlyDTO dudto) {
		// TODO Auto-generated method stub
		sqlSession.insert(ns+"", dudto);
	}

	@Override
	public void updateDevice(ElderlyDTO dudto) {
		// TODO Auto-generated method stub
		sqlSession.update(ns+"", dudto);
	}

	@Override
	public void deleteDevice(int dnum) {
		// TODO Auto-generated method stub
		sqlSession.delete(ns+"", dnum);
	}

}
