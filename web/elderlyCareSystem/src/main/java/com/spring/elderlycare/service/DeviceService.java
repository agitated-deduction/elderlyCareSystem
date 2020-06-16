package com.spring.elderlycare.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.ElderlyDTO;

@Component
public interface DeviceService {
	public List<ElderlyDTO>devicesList(String id);
	public void deviceRegistration(ElderlyDTO dudto);
	public ElderlyDTO deviceInfo(int dnum);
	public ElderlyDTO selectDeviceInfo(int dnum);
	public void deleteDevice(int dnum);
	
}
