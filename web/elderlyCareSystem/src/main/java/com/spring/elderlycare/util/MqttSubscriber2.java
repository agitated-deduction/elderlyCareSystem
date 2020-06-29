package com.spring.elderlycare.util;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MqttSubscriber2 implements MqttCallback{
	private String brokerURL = "";
	private String clientId = "AsyncTest";
	private MemoryPersistence persistence = new MemoryPersistence();
	
	
	private Logger logger = LoggerFactory.getLogger(MqttSubscriber2.class);
	
	public void mqttInit(String broker, int port) {
		this.brokerURL = "tcp://"+broker+":"+port;
		try {
		MqttAsyncClient client = new MqttAsyncClient(brokerURL, clientId, persistence);
		}catch(Exception me){
			
		}
	}
	@Override
	public void connectionLost(Throwable cause) {
		logger.info("connection lost");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("topic: " + topic);
        System.out.println("message: " + new String(message.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		logger.info("delivery complete");
	}
	
}
