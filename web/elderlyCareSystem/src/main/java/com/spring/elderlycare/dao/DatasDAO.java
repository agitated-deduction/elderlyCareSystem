package com.spring.elderlycare.dao;

import org.springframework.stereotype.Component;

import com.spring.elderlycare.dto.DatasDTO;

@Component
public interface DatasDAO  {
	public void insertDataEvent(DatasDTO datasdto);

}
