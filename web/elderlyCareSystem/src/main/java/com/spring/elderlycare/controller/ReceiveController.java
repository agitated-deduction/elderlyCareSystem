package com.spring.elderlycare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.elderlycare.dto.Datas2DTO;
import com.spring.elderlycare.service.DataService;

@RestController
@RequestMapping("/datas")
public class ReceiveController {
	@Autowired private DataService service;
	
	@RequestMapping(method = RequestMethod.POST,
			headers= {"Content-type=application/json"})
	@Async
	public String receiveData(Datas2DTO dto){
			service.insertBandDatas(dto);
			return "receive test";
	}
}
