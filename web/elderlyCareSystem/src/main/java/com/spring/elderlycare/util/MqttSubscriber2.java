package com.spring.elderlycare.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.elderlycare.dao.DatasDAO;
import com.spring.elderlycare.dto.DatasDTO;
import com.spring.elderlycare.service.MqttTaskService;

@Component
public class MqttSubscriber2 implements MqttCallback{
	private String brokerURL = "";
	private String clientId = "AsyncTest";
	private MemoryPersistence persistence = new MemoryPersistence();
	
	MqttAsyncClient client = null;
	MqttConnectOptions options = null;
	
	@Autowired DatasDAO dao;
	@Autowired DatasDTO dto;
	@Autowired MqttTaskService service;
	
	Lock lock = new ReentrantLock();
	
	private Logger logger = LoggerFactory.getLogger(MqttSubscriber2.class);
	
	public void mqttSubscribe(String broker, int port, String topic, int elderly) {
		this.brokerURL = "tcp://"+broker+":"+port;
		try {
			dto.setElderly(elderly);
			client = new MqttAsyncClient(brokerURL, clientId, persistence);
			options = new MqttConnectOptions();
			options.setCleanSession(true);
			
			IMqttToken token = client.connect(options);
			token.waitForCompletion();
			
			client.setCallback(new MqttSubscriber2());
			client.subscribe(topic, 2);
		}catch(Exception me){
			if(me instanceof MqttException) {
				System.out.println("reason " + ((MqttException) me).getReasonCode());
			}
			System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
		}
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		logger.info("connection lost");
		logger.info(cause.toString());
		cause.printStackTrace();
		//disconnect하고 reconnect
		/*try {
			client.reconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("topic: " + topic);
        System.out.println("message: " + new String(message.getPayload()));
		messageProcessing(topic, message);
		/*
		 * lock.lock(); service.eventProcessing(topic, message.toString());
		 * lock.unlock();
		 */
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		logger.info("delivery complete");
	}
	
	private void messageProcessing(String topic, MqttMessage message) {
		try { 
			float data = Float.parseFloat(message.toString()); 
			switch(topic) { 
			case "home/humid": 
				//dto.setHumid(data);
				//dao.insertDataEvent(dto);
				break; 
			case "home/temp": 
				//dto.setTemp(data);
				//dao.insertDataEvent(dto);
				break; 
			case "home/alone": //3일 이상 움직임 없을 경우 알림 울리도록.
				alertToApp(topic); 
				break; 
			case "home/gas": //서버에 저장함. 무슨 객체? 암튼 몇분이내에 가스 검출 있을 경우 알림 울리도록.
				alertToApp(topic);
				break; 
			}
			
		  }catch(NumberFormatException e) { 
			  //"home/vid"
			  //인코딩 되어서 오는 파일 디코딩해서 저장하든 뭐 하든 알아서
			  /*https://addio3305.tistory.com/83 참고해서 수정하기
		        if(topic.equals("home/vid")) {
		        Decoder decoder = Base64.getDecoder();
		        byte[] decodesBytes = decoder.decode(message.getPayload());
		        System.out.println("testestest"+decodesBytes.toString());
		        
		        try {
		        	File outFile = new File("./test");
		        	FileOutputStream fileOutputStream = new FileOutputStream(outFile);
		        	fileOutputStream.write(decodesBytes);
		        	fileOutputStream.close();
		        	
		        }catch(Throwable e) {
		        	e.printStackTrace(System.out);
		        }
		        }*/
		  }
	}
	private void alertToApp(String topic) {
		/*알림 시스템. 어플로 알림 전송. 
		*1. humidity, temperature 이상범위
		*2. 밤 중 움직임 감지 영상
		*3. 3일 이상 움직임이 없을 시
		*4. 이상 가스 검출 시
		*/
	}
	
}
