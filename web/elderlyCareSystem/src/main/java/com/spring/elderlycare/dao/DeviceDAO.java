package com.spring.elderlycare.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.ElderlyDTO;

@Component
public interface DeviceDAO {
	public List<ElderlyDTO> selectList(String id);
	public ElderlyDTO selectOne(int dnum);
	public void insertDevice(ElderlyDTO dudto);
	public void updateDevice(ElderlyDTO dudto);
	public void deleteDevice(int dnum);
	
}
