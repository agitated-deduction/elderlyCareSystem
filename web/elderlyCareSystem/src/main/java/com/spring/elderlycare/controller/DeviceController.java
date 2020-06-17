package com.spring.elderlycare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.spring.elderlycare.dto.DevicesDTO;
import com.spring.elderlycare.dto.ElderlyDTO;
import com.spring.elderlycare.service.DeviceService;

@RestController
@RequestMapping("/devices")
public class DeviceController {
	@Autowired private DeviceService service;
	@Autowired private ElderlyDTO edto;
	@Autowired private DevicesDTO ddto;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<ElderlyDTO> deviceList(Model model, @SessionAttribute("uid") String uid) {
		List<ElderlyDTO> list = service.devicesList(uid);
		
		return list;
	}
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public ModelAndView form(ModelAndView mav,
			@RequestParam("inp_e_name")String ename,
			@RequestParam("inp_e_birth")String ebirth,
			@RequestParam("inp_e_tel")String etel,
			@RequestParam("inp_e_addr")String eaddr,
			@RequestParam("inp_homeiot")String homeIoT,
			@RequestParam("inp_bandiot")String bandIoT, 
			@SessionAttribute("uid")String uid) {
		mav.setViewName("redirect:/devices");
		
		edto.setEname(ename);
		edto.setEbirth(ebirth);
		edto.setEaddr(eaddr);
		edto.setEtel(etel);
		
		ddto.setHomeIoT(homeIoT);
		ddto.setBandIoT(bandIoT);
		
		//BundleDTO bdto = new BundleDTO(ddto, edto);
		service.deviceRegistration(edto, ddto, uid);
		
		return mav;
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView deviceRegistration(ModelAndView mav) {
		mav.setViewName("device/registration");
		
		return mav;
	}
	@RequestMapping(value = "/{num}", method = RequestMethod.GET)
	public ElderlyDTO deviceInfo(Model model, @PathVariable("num") int dnum) {
		edto = service.deviceInfo(dnum);
		return edto;
	}
	@RequestMapping(value = "/{num}", method = RequestMethod.PUT)
	public ElderlyDTO deviceInfoModify(Model model) {
		
		return null;
	}
	@RequestMapping(value = "/{num}", method = RequestMethod.DELETE)
	public ModelAndView deviceDelete(ModelAndView mav, @PathVariable("num") int num) {
		service.deleteDevice(num);
		return null;
	}
}
