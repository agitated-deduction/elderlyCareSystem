package com.spring.elderlycare.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.elderlycare.dto.Datas2DTO;
import com.spring.elderlycare.dto.DatasDTO;

@Repository("datasDAO")
public class DatasDAOImpl implements DatasDAO{
	@Autowired
	private SqlSession sqlSession;
	
	private static final String ns = "com.spring.elderlycare.dao.DatasDAOImpl.";
	/*@Override
	public void insertDataEvent(DatasDTO datasdto) {
		sqlSession.insert(ns+"log", datasdto);
	}*/


	@Override
	public List<DatasDTO> selectHumids(int num) {
		return sqlSession.selectList(ns+"selectHumids", num);
	}

	@Override
	public List<DatasDTO> selectTemps(int num) {
		return sqlSession.selectList(ns+"selectTemps", num);
	}
	

	@Override
	public void insertBandDatas(Datas2DTO dto) {
		sqlSession.insert(ns+"log2", dto);
		
	}
	@Override
	public Datas2DTO selectCurHealthData(int num) {
		return sqlSession.selectOne(ns+"curHealth", num);
	}

	@Override
	public DatasDTO selectCurHTData(int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Datas2DTO> selectHealths(int num) {
		return sqlSession.selectList(ns+"selectHealths", num);
		
	}

	@Override
	public List<DatasDTO> selectHTs(int num) {
		return sqlSession.selectList(ns+"selectHts", num);
	}
	
	
}
