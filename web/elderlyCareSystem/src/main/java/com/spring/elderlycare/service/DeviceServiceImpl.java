package com.spring.elderlycare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.elderlycare.dao.DeviceDAO;
import com.spring.elderlycare.dto.DevicesDTO;
import com.spring.elderlycare.dto.ElderlyDTO;

@Service
public class DeviceServiceImpl implements DeviceService{
	
	@Autowired private DeviceDAO ddao;
	
	
	@Override
	public List<ElderlyDTO> devicesList(String id) {
		// TODO Auto-generated method stub
		List<ElderlyDTO> list = ddao.selectList(id);
		return list;
	}

	@Override
	public void deviceRegistration(ElderlyDTO edto, DevicesDTO ddto, String id) {
		// TODO Auto-generated method stub
		ddao.insertDevice(edto, ddto, id);
	}

	@Override
	public ElderlyDTO elderlyInfo(int dnum) {
		// TODO Auto-generated method stub
		
		return ddao.selectOne(dnum);
	}

	@Override
	public DevicesDTO deviceInfo(int dnum) {
		return ddao.selectDevice(dnum);
	}

	@Override
	public void deleteDevice(int dnum) {
		// TODO Auto-generated method stub
		ddao.deleteDevice(dnum);
	}

}
