package com.spring.elderlycare.controller;

import java.util.concurrent.Callable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.elderlycare.dto.Datas2DTO;
import com.spring.elderlycare.service.DataService;

@RestController
@RequestMapping("/datas")
public class ReceiveController {
	private final DataService dataService;
	
	public ReceiveController(DataService dataService) {
		this.dataService = dataService;
	}
	
	@RequestMapping(value = "/{num}", method = RequestMethod.POST,
			headers= {"Content-type=application/json"})
	public Callable<String> receiveData(Datas2DTO dto){
		return()->{
			dataService.insertBandDatas(dto);
			
			return "Receive";
		};
	}
}
