package com.spring.elderlycare.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.Datas2DTO;

@Component
public interface DataService {
	public Map<String, Object> getHumTemp(int num);
	public void insertBandDatas(Datas2DTO dto);
	public Datas2DTO selectCurHealthData(int num);
}
