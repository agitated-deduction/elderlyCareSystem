package com.spring.elderlycare.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.elderlycare.dto.DevicesDTO;
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
		//return null;
	}

	@Override
	public ElderlyDTO selectOne(int dnum) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(ns+"selectDevice", dnum);
		//return null;
	}

	@Override
	public void insertDevice(ElderlyDTO edto, DevicesDTO ddto, String id) {
		// TODO Auto-generated method stub
		
		sqlSession.insert(ns+"insertElderly", edto);
		int eld = edto.getEkey();
		ddto.setElderly(eld);
		sqlSession.insert(ns+"insertDevice", ddto);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("elderly", eld);
		map.put("staff", id);
		sqlSession.insert(ns+"manage", map);
	}

	@Override
	public void updateDevice(ElderlyDTO dudto) {
		// TODO Auto-generated method stub
		//sqlSession.update(ns+"", dudto);
	}

	@Override
	public void deleteDevice(int dnum) {
		// TODO Auto-generated method stub
		sqlSession.delete(ns+"deleteDevice", dnum);
	}

}
