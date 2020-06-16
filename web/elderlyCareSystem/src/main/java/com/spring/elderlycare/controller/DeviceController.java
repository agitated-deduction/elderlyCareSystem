package com.spring.elderlycare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.spring.elderlycare.dto.ElderlyDTO;
import com.spring.elderlycare.service.DeviceService;

@RestController
@RequestMapping("/devices")
public class DeviceController {
	@Autowired private DeviceService service;
	@Autowired private ElderlyDTO edto;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<ElderlyDTO> deviceList(Model model, @SessionAttribute("uid") String uid) {
		List<ElderlyDTO> list = service.devicesList(uid);
		
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView deviceRegistration(ModelAndView mav) {
		
		
		return mav;
	}
	@RequestMapping(value = "/{num}", method = RequestMethod.GET)
	public ElderlyDTO deviceInfo(Model model) {
		
		return null;
	}
	@RequestMapping(value = "/{num}", method = RequestMethod.PUT)
	public ElderlyDTO deviceInfoModify(Model model) {
		
		return null;
	}
	@RequestMapping(value = "/{num}", method = RequestMethod.DELETE)
	public ElderlyDTO deviceDelete(Model model) {
		
		return null;
	}
}
