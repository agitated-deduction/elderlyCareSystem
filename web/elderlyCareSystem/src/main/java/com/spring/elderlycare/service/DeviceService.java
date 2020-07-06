package com.spring.elderlycare.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.DevicesDTO;
import com.spring.elderlycare.dto.ElderlyDTO;

@Component
public interface DeviceService {
	public List<ElderlyDTO>devicesList(String id);
	public void deviceRegistration(ElderlyDTO edto, DevicesDTO ddto, String id);
	public ElderlyDTO elderlyInfo(int dnum);
	public DevicesDTO deviceInfo(int dnum);
	public void deleteDevice(int dnum);
	
}
