package com.spring.elderlycare.service;

import com.spring.elderlycare.dto.DevicesDTO;

//@Service
public class MqttTaskServiceImpl implements MqttTaskService{

	@Override
	public void runningBackground(DevicesDTO ddto) {
		// TODO Auto-generated method stub
		
	}
	/*
	//@Autowired MQTTSubscriber subscriber;
	@Autowired MqttSubscriber2 subscriber;
	//@Autowired DatasDAO dao;
	//@Autowired MQTTPublisher publisher;
	
	@Async
	public void runningBackground(DevicesDTO ddto) {
		//subscriber.config("127.0.0.1", 1883, false, false);
		//subscriber.subscribeMessage("home/#");
		System.out.println("======mqtt async test========");
		//subscriber.config(ddto.getHomeIoT(), 1883, false, false);
		//subscriber.subscribeMessage("home/#", ddto.getElderly());
		//subscriber.publishMessage("home/1", "message from webserver");
		//while(true);
		//subscriber.mqttSubscribe("222.106.22.114", 1883, "home/#", ddto.getElderly());
		subscriber.mqttSubscribe(ddto.getHomeIoT(), 1883, "home/#", ddto.getElderly());
	}
	*/
	/*
	 * 받아온 데이터 timestamp 찍기. util에서 직접 바로 해야되나?
	 */
	}

