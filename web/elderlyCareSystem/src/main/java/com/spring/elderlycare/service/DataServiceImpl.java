package com.spring.elderlycare.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.elderlycare.dao.DatasDAO;

@Service
public class DataServiceImpl implements DataService{
	@Autowired DatasDAO dao;

	@Override
	public Map<String, Object> getHumTemp(int num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("humid", dao.selectHumids(num));
		map.put("temp", dao.selectTemps(num));
		return map;
	}
	
	
}