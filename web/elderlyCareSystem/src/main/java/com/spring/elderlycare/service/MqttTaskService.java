package com.spring.elderlycare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.spring.elderlycare.util.MQTTSubscriber;

@Service("mqttTaskService")
public class MqttTaskService {
	
	@Autowired MQTTSubscriber subscriber;
	
	@Async
	public void runningBackground(String temp) {
		//subscriber.config("222.106.22.114", 1883, false, false);
		//subscriber.subscribeMessage("home/#");
		System.out.println("======mqtt async test========");
		subscriber.config("127.0.0.1", 1883, false, false);
		subscriber.subscribeMessage("test/#");
		while(true);
	}
}
