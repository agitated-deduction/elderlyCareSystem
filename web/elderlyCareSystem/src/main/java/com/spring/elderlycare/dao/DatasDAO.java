package com.spring.elderlycare.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.DatasDTO;

@Component
public interface DatasDAO  {
	//public void insertDataEvent(DatasDTO datasdto);
	public List<DatasDTO> selectHumids(int num);
	public List<DatasDTO> selectTemps(int num);
}