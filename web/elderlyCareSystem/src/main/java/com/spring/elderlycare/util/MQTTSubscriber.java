package com.spring.elderlycare.util;


import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.spring.elderlycare.dao.DatasDAO;
//import com.spring.elderlycare.dto.DatasDTO;
//import com.spring.elderlycare.dto.DevicesDTO;

/*
 * ref
 * https://www.monirthought.com/2017/11/eclipse-paho-java-client-mqtt-client.html
 * 
 */
//@Component
public class MQTTSubscriber extends MQTTConfig implements MqttCallback{
	
	private String brokerURL = null;
	final private String colon = ":";
	final private String clientId = "user00(test)";
	
	private MqttClient mqttClient = null;
	private MqttConnectOptions connectOptions = null;
	private MemoryPersistence persistence = null;
	
	private static final Logger logger = LoggerFactory.getLogger(MQTTSubscriber.class);
	
	//@Autowired DatasDAO dao;
	//@Autowired DatasDTO dto;
	
	public MQTTSubscriber() {
		logger.info("init");
		//this.config();
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		logger.info("connection lost");
	}
	
	public void publishMessage(String topic, String message) {
		 logger.info("publish Message");
	  try {
		  MqttMessage mqttmessage = new MqttMessage(message.getBytes());
		  mqttmessage.setQos(this.qos);
	   this.mqttClient.publish(topic, mqttmessage);
	  } catch (MqttException me) {
	   me.printStackTrace();
	  }
	 }
	

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println();
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Message Arrived at Time: " + time + "  Topic: " + topic + "  Message: "
		  + new String(message.getPayload()));
		System.out.println("***********************************************************************");
		System.out.println();
		//dto.setGas(false);
		//float data = Float.parseFloat(message.toString());
		//if (topic.equals("home/humid")) 
		//	dto.setHumid(data);
		//else if(topic.equals("home/temp"))
		//	dto.setTemp(data);
		
		//dao.insertDataEvent(dto);
	}
	

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		logger.info("delivery completed");
	}
	
	public void subscribeMessage(String topic, int elderly) {
		 logger.info("subscribe Message");
	  try {
	   this.mqttClient.subscribe(topic, this.qos);
	   //dto.setElderly(elderly);
	  } catch (MqttException me) {
	   me.printStackTrace();
	  }
	 }
	
	 public void disconnect() {
		 logger.info("disconnect");
		  try {
		   this.mqttClient.disconnect();
		  } catch (MqttException me) {
		   logger.error("ERROR", me);
		  }
		 }

	@Override
	/*protected*/public void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
		// TODO Auto-generated method stub
		String protocol = ssl?this.SSL:this.TCP;
		this.brokerURL = protocol+broker+colon+port;
		this.persistence = new MemoryPersistence();
		this.connectOptions = new MqttConnectOptions();
		
		logger.info(brokerURL);
		try {
			this.mqttClient = new MqttClient(brokerURL, clientId, persistence);
			this.connectOptions.setCleanSession(true);
			if(withUserNamePass) {
				if(password!=null) this.connectOptions.setPassword(this.password.toCharArray());
				if(userName !=null) this.connectOptions.setUserName(this.userName);
			}
			this.mqttClient.connect(this.connectOptions);
			this.mqttClient.setCallback(this);
		}catch(MqttException me) {
			me.printStackTrace();
		}
	}

	@Override
	/*protected*/public void config() {
		logger.info("config()");
		// TODO Auto-generated method stub
		this.brokerURL = this.TCP+this.broker+colon+this.port;
		this.persistence = new MemoryPersistence();
		this.connectOptions = new MqttConnectOptions();
		try {
			  this.mqttClient = new MqttClient(brokerURL, clientId, persistence);
			  this.connectOptions.setCleanSession(true);
			  this.mqttClient.connect(this.connectOptions);
			  this.mqttClient.setCallback(this);
			 } catch (MqttException me) {
			  me.printStackTrace();
		  }
	}
	
	

}
