package com.spring.elderlycare.service;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface DataService {
	public Map<String, Object> getHumTemp(int num);
}
