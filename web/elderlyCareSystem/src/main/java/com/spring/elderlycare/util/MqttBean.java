package com.spring.elderlycare.util;

import java.io.IOException;

import org.springframework.scheduling.annotation.Async;


 public class MqttBean { private final String mqttProcess =
 "D:\\1elderlyproject\\web\\mqttstarternoreconnect.jar";
 
 	@Async 
 	void doSomthing() { 
 		try {
 			Runtime.getRuntime().exec("java -jar "+mqttProcess); 
 		} catch (IOException e){ 
 			e.printStackTrace(); 
 		} 
 		//return new AsyncResult<String>("Result"); 
	 } 
}

/*
 * public class MqttBean {
 * 
 * @Async void doSomthing() { MqttService client = new MqttService();
 * 
 * List<Map<String, Object>> list = client.getIoTList();
 * 
 * for(Map<String, Object>m : list) {
 * client.mqttSubscribe((String)m.get("homeIoT"), 1883, "home/#"); } } }
 */