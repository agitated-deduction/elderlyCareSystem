package com.spring.elderlycare.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.spring.elderlycare.dto.DatasDTO;
import com.spring.elderlycare.dto.DevicesDTO;
import com.spring.elderlycare.dto.ElderlyDTO;
import com.spring.elderlycare.service.DataService;
import com.spring.elderlycare.service.DeviceService;

@SessionAttributes("uid")
@RestController
@RequestMapping("/devices")
public class DeviceController {
	@Autowired private DeviceService service;
	@Autowired private DataService dataservice;
	//@Autowired private MqttTaskService mqtt;
	@Autowired private ElderlyDTO edto;
	@Autowired private DevicesDTO ddto;
	@Autowired private DatasDTO datadto;
	private final Logger logger = LoggerFactory.getLogger(DeviceController.class);

	/*
	 * 
	 * 
	 * devices 부분입니다.
	public List<ElderlyDTO> deviceList(HttpSession httpSession, @CookieValue(value="Cookie",required=false) Cookie cookie) {
		
		logger.info("++++++++++++"+"DeviceList"+"+++++++++++++"); 
		logger.info(httpSession.getId()); //요청한 사람의 쿠키 확인
		logger.info(httpSession.getAttribute("uid")); //세션 내 uid 확인
		List<ElderlyDTO> list = service.devicesList(httpSession.getAttribute("uid").toString());//
			return list;
	} 
	// 어노테이션 @CookieValue로 쿠키를 받아오려 했으나 null값 확인됩니다. -> HttpSession값을 통해 쿠키를 확인하였습니다.
	// 웹에서 /devices 요청시 : <session 내 uid & 쿠키>, <해당 uid의 devices list return>까지 확인하였습니다.
	// 앱에서 /devices 요청시 : <getAttribute("uid")> -> nullpoint error & <empty body return>
	  				   + <앱에서 return받은 응답의 header에 새로운 쿠키 생성됨> 확인하였습니다.
	 * 
	 * 
	 * 
	 * */
	@RequestMapping(method = RequestMethod.GET)
	public List<ElderlyDTO> deviceList(HttpSession httpSession) {
		List<ElderlyDTO> list = service.devicesList((String)httpSession.getAttribute("uid"));
				
		return list;
	}
	/*********************************/
	/*********************************/
	@RequestMapping(value = "/form", method = RequestMethod.POST,
			headers= {"Content-type=application/json"})
	public boolean form(HttpSession httpSession, @RequestBody Map<String, String> json) {
		logger.info(json.toString());
		edto.setEname(json.get("ename"));
		edto.setEbirth(json.get("ebirth"));
		edto.setEaddr(json.get("eaddr"));
		edto.setEtel(json.get("etel"));
		
		ddto.setHomeIoT(json.get("homeIoT"));
		ddto.setBandIoT(json.get("bandIoT"));
		
		logger.info(edto.getEname());
		logger.info(ddto.getBandIoT());
		
		service.deviceRegistration(edto, ddto, (String)httpSession.getAttribute("uid"));
		
		return true;
	}
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView deviceRegistration(ModelAndView mav) {
		mav.setViewName("device/registration");
		
		return mav;
	}
	@RequestMapping(value = "/{num}", method = RequestMethod.GET)
	public ElderlyDTO deviceInfo(Model model, @PathVariable("num") int dnum) {
		edto = service.elderlyInfo(dnum);
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
	/*
	 *MQTT 통신 activate, 직접 버튼 클릭하도록 일단 구현. 서버 재시작시 알아서 전부 세팅되도록 수정하고싶음. 
	 */
	/*@RequestMapping(value = "/{num}/mqtt-thread")
	public ModelAndView activateMQTTThread(ModelAndView mav, @PathVariable("num") int dnum) {
		ddto = service.deviceInfo(dnum);
		logger.info("mqtt-thread : "+ddto.getHomeIoT());
		
		mqtt.runningBackground(ddto); //ㅅㄷㄴㅅ
		
		mav.setViewName("redirect:/");
		
		return mav;
	}*/
	@RequestMapping("/{num}/data")
	public Map<String, Object> viewData(Model model, @PathVariable("num") int num) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = dataservice.getHumTemp(num);
		//맥박, 걸음 수, gps가져와서 put
		return map;
	}
}
